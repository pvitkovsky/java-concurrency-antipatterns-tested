package work.fortunate.jcip.examples.non_atomic_check_then_act;

import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;


/**
 * Adapted from JCIP 2.2
 * Non-atomic compound actions on a shared counter;
 */
@NotThreadSafe
public class RacyCounter {

    private int count;

    public void increment() {
        count = count + 1;
    }

    public int getCount() {
        return count;
    }
}
