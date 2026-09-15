package work.fortunate.jcip.examples.escaping_object;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

import java.util.UUID;

@JCStressTest
@Outcome(id = "1", expect = Expect.ACCEPTABLE, desc = "Object fully initialised")
@Outcome(id = "0", expect = Expect.ACCEPTABLE, desc = "Object is null")
@Outcome(id = "-1", expect = Expect.FORBIDDEN, desc = "💥 Caught leaked reference! Read uninitialized final field.")
@State
public class EscapingObjectTest {

    private final EscapingContext context = new EscapingContext();

    @Actor
    public void writer() {
        new EscapingObject(context);
    }

    @Actor
    public void reader(I_Result r) {
        EscapingObject obj = context.activeInstance;
        if(obj == null){
            r.r1 = 0;
            return;
        }
        UUID id = obj.id;
        if(id != null){
            r.r1 = 1;
            return;
        }
        r.r1 = -1;
    }
}
