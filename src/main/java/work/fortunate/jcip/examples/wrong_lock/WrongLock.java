package work.fortunate.jcip.examples.wrong_lock;

/**
 * Pattern when the invariant seems locked, but on wrong class!
 */
public class WrongLock {

    public int total;

    public void increment(){
        total++;
    }
}
