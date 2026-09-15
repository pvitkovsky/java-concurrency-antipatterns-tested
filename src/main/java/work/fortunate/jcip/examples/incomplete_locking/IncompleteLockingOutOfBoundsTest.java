package work.fortunate.jcip.examples.incomplete_locking;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

import java.util.stream.IntStream;

@JCStressTest
@Outcome(id = "0", expect = Expect.ACCEPTABLE, desc = "Invariant held.")
@Outcome(id = "-1", expect = Expect.FORBIDDEN, desc = "💥 Race Condition! Data corruption example.")
@State
public class IncompleteLockingOutOfBoundsTest {

    private IncompleteLocking incompleteLocking = new IncompleteLocking();
    private volatile boolean writerFinished = false;

    private static final String[] LETTERS = new String[]{"JAVA_CONCURRENCY", "IN_PRACTICE", "IS_HARDER", "IS_EASIER", "DEPENDING_ON", "YOUR_TIMING"};

    @Actor
    public void writer() {
        IntStream.range(0, 1023).parallel().forEach(i -> {
            incompleteLocking.record(LETTERS[i % LETTERS.length]);
        });
        writerFinished = true;
    }

    @Actor
    public void reader(I_Result r) {
        while (!writerFinished) {
            String s = incompleteLocking.peekUnsafe();
            if (s.indexOf('\0') >= 0) {
                r.r1 = -1;
            } else {
                r.r1 = 0;
            }
        }
    }
}


/*
 *
 *
 * In Java 9+, String(AbstractStringBuilder asb, Void sig) handles it completely differently. Look closely at how the variables are extracted and used in the code you pasted:
 *
 * byte[] val = asb.getValue();
 *    // 1. Gets the array pointerint length = asb.length();
 *    // 2. Gets the current count tracking integer
 * ...this.value = Arrays.copyOfRange(val, 0, length); // Or length << 1 for UTF16
 *
 * Because this is a completely un-synchronized read, two unique race conditions can happen when writer() is mid-resize, but neither one can throw an ArrayIndexOutOfBoundsException inside toString() on your x86 machine:
 * ## Case A: Reading the Old Array, but the New Length
 *
 *    1. The writer() allocates a brand-new large array, copies data, and assigns it to this.value. It then updates this.count to a massive number (e.g., 500).
 *    2. The reader() executes. Because of x86 store-store ordering, the hardware cache line updates are strict, but if a data race splits the field reads, the reader might grab the old val array (length 16) but the new length integer (500).
 *    3. It passes them to Arrays.copyOfRange(val, 0, 500).
 *
 * Why it doesn't crash: If you look at the source code for java.work.fortunate.jcip.util.Arrays.copyOfRange(byte[] original, int from, int to), it handles a to index that exceeds the original array length by padding the rest of the new array with zeros (\0). It explicitly does not throw an ArrayIndexOutOfBoundsException. Instead, it allocates a 500-byte array, copies the 16 characters it can find, and fills the remaining 484 slots with null bytes.
 * ## Case B: Reading the New Array, but the Old Length
 *
 *    1. The writer() updates the array reference to the new large array, but hasn't updated the length integer yet.
 *    2. The reader() grabs the new large array but the old short length (e.g., 16).
 *    3. Arrays.copyOfRange copies the first 16 bytes of the new array safely.
 *
 * ------------------------------
 * ## The Reality of Modern StringBuilder Data Races
 * The real "failure" of calling peekUnsafe() on an active StringBuilder is that it silently returns garbage text or a string heavily padded with hidden \0 null blocks. [3]
 *
 * [1] [https://cr.openjdk.org](https://cr.openjdk.org/~mr/rev/8249205/src/java.base/share/classes/java/lang/String.java.html)
 * [2] [https://blog.csdn.net](https://blog.csdn.net/qq_39618369/article/details/108000135)
 * [3] [https://www.geeksforgeeks.org](https://www.geeksforgeeks.org/java/java-util-arrays-copyofrange-java/)
 */
