package work.fortunate.jcip.examples.no_visibility;


import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;

/**
 * adapted from JCIP 3.1
 * if number is set before ready, jcstress tests pass on x86 CPUs
 * on ARM CPUs non-volatile state variables might cause serious bugs
 */
@NotThreadSafe
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
