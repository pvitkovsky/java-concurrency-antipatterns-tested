package work.fortunate.jcip.examples.wrong_lock;


import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

@JCStressTest
@Outcome(id = "2", expect = Expect.ACCEPTABLE, desc = "Invariant held.")
@Outcome(id = "1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "💥 Race Condition! Lost update.")
@Outcome(id = "0", expect = Expect.FORBIDDEN, desc = "💥 Race Condition! Two lost updates, how did you get here?.")
@State
public class WrongLockTest {

    WrongLock wl = new WrongLock();

    @Actor
    public void createA(){
        WrongLockRunner wrA = new WrongLockRunner();
        wrA.increment(wl);
    }

    @Actor
    public void createB(){
        WrongLockRunner wrB = new WrongLockRunner();
        wrB.increment(wl);
    }

    @Arbiter
    public void checkResults(I_Result r){
        r.r1 = wl.total;
    }
}
