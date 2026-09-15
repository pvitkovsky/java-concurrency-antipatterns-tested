package work.fortunate.jcip.examples.non_atomic_check_then_act;

import net.jcip.annotations.NotThreadSafe;


/**
 * Non-atomic compound actions on a shared counter and a volatile field used
 * incorrectly for a multi-step update.
 *
 * spotbugs ignores this;
 */
@NotThreadSafe
public class RacyCounter {

    private int count;
    private volatile long lastNanos;

    public void increment() {
        count = count + 1;
        lastNanos = System.nanoTime();
    }

    public int getCount() {
        return count;
    }

    public long getLastNanos() {
        return lastNanos;
    }

    /** Classic check-then-act race. */
    public void resetIfPositive() {
        if (count > 0) {
            count = 0;
        }
    }
}
