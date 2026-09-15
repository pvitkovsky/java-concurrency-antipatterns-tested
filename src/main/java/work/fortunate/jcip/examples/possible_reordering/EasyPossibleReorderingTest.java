package work.fortunate.jcip.examples.possible_reordering;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

/**
 * mvn clean verify
 * java -jar target/jcstress.jar -t PossibleReordering -f 10 -v
 */
@JCStressTest
@Outcome(id = "1", expect = Expect.ACCEPTABLE, desc = "Both updates succeeded.")
@Outcome(id = "-1", expect = Expect.FORBIDDEN, desc = "💥 Race Condition! Thread interleaving")
@Outcome(id = "-2", expect = Expect.FORBIDDEN, desc = "💥 Race Condition! Store-Load reordering caused a return of stale values.")
@State
public class EasyPossibleReorderingTest {

    private final EasyPossibleReordering reordering = new EasyPossibleReordering();

    @Actor
    public void runA() {
        reordering.runA();
    }
    @Actor
    public void runB() {
        reordering.runB();
    }
    @Arbiter
    public void arbiter(I_Result r) {
        EasyPossibleReordering.IntPair res = reordering.peek();
        if(res.a() == 0 && res.b() == 0) {
            r.r1 = -2;
            return;
        }
        if(res.a() == 0 || res.b() == 0) {
            r.r1 = -1;
            return;
        }
        r.r1 = 1;
    }

}

