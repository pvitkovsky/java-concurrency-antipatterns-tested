package work.fortunate.jcip.examples.no_visibility;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

// TODO: spotbugs ignores this;
@JCStressTest
@Outcome(id = "1", expect = Expect.ACCEPTABLE, desc = "Successful.")
@Outcome(id = "-1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "💥 Race Condition! Wrong number.")
@State
public class NoVisibilityTest {

    // TODO: broken because the testing code manages visibility
    private final NoVisibility noVisibility = new NoVisibility();

    @Actor
    public void thread1(I_Result r) {
        try {
            int res = noVisibility.test();
            if(res != 42){
                r.r1 = -1;
                return;
            }
            r.r1 = 1;
        }  catch (InterruptedException e) {
        }
    }
}
