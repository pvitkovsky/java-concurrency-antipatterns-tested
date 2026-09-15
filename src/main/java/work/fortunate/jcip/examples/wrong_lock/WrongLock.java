package work.fortunate.jcip.examples.wrong_lock;

import net.jcip.annotations.ThreadSafe;

/**
 * Pattern when the invariant seems locked, but on wrong class!
 */
@ThreadSafe
public class WrongLock {

    private int total;

    public int getTotal() {
        return total;
    }

    public void increment(){
        total++;
    }
}
