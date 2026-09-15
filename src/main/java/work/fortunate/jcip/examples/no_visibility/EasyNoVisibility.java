package work.fortunate.jcip.examples.no_visibility;

// TODO: spotbugs ignores this;
/**
 * adapted from JCIP 3.1
 */
public class EasyNoVisibility {
    private boolean ready = false;
    private int number;

    public int getNumber(){
        while (!ready) {
            Thread.yield();
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
