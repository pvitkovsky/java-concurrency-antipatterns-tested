package work.fortunate.jcip.examples.non_atomic_check_then_act;

import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;


/**
 * Non-atomic compound actions on a shared counter;
 */
@ThreadSafe
public class RacyCounter {

    private int count;

    public void increment() {
        count = count + 1;
    }

    public int getCount() {
        return count;
    }
}
