package work.fortunate.jcip.examples.no_visibility;


import net.jcip.annotations.ThreadSafe;

/**
 * adapted from JCIP 3.1
 *
 */
@ThreadSafe
public class EasyNoVisibility {
    private boolean ready = false;
    private int number;

    public int getNumber(){
        while (!ready) {
            Thread.yield(); // ErrorProne: Relying on the thread scheduler is discouraged.
        }
       return number;
    }

    /**
     * Stores are not reordered with other stores
     */
    public void ready()  {
        ready = true;
        number = 42;
    }
}
