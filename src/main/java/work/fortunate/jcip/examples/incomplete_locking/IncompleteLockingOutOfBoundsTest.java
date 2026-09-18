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
