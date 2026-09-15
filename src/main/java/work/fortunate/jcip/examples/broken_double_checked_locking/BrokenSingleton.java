package work.fortunate.jcip.examples.broken_double_checked_locking;

import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;

/**
 * Broken double-checked locking (non-volatile instance).
 *
 */
@ThreadSafe
public class BrokenSingleton {

    private static BrokenSingleton instance;

    private final byte[] payload = new byte[64];

    private BrokenSingleton() throws InterruptedException {
        System.out.println("Creating a new instance of BrokenSingleton");
        payload[0] = 1;
    }

    public static BrokenSingleton getInstance() throws InterruptedException {
        if (instance == null) {
            // ErrorProne:  [DoubleCheckedLocking] Double-checked locking on non-volatile fields is unsafe
            // SpotBugs: Possible double-check
            synchronized (BrokenSingleton.class) {
                if (instance == null) {
                    instance = new BrokenSingleton();
                }
            }
        }
        return instance;
    }

    public byte first() {
        return payload[0];
    }
}

