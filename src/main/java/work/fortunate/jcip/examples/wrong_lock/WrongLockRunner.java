package work.fortunate.jcip.examples.wrong_lock;

/**
 * Enough to have more than one WrongLockRunner and call increment concurrently to get a race condition
 */
public class WrongLockRunner {
    public synchronized void increment(WrongLock wl )  {
        wl.increment();
    }
    public synchronized int get(WrongLock wl )  {
        return wl.getTotal();
    }
}
