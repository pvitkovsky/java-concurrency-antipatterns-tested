package work.fortunate.jcip.examples.no_visibility;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

@JCStressTest
@Outcome(id = "1", expect = Expect.ACCEPTABLE, desc = "Successful.")
@Outcome(id = "-1", expect = Expect.FORBIDDEN, desc = "💥 Race Condition! Wrong number.")
@State
public class EasyNoVisibilityTest {

    private final EasyNoVisibility noVisibility = new EasyNoVisibility();

    @Actor
    public void writer(){
        noVisibility.ready();
    }

    @Actor
    public void reader(I_Result r) {
        int res = noVisibility.getNumber();
        if(res != 42){
            r.r1 = -1;
            return;
        }
        r.r1 = 1;
    }
}
