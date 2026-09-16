package work.fortunate.jcip.examples.wrong_lock;

import net.jcip.annotations.ThreadSafe;

/**
 * Non-JCIP example inspired by Oracle Java SE exam
 * Pattern when the state seems locked, but on the wrong class!
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
