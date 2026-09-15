package work.fortunate.jcip.examples.possible_reordering;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

/**
 * mvn clean verify
 * java -jar target/jcstress.jar -t PossibleReordering -f 10 -v
 */
@JCStressTest
@Outcome(id = "1", expect = Expect.ACCEPTABLE, desc = "Both updates succeeded.")
@Outcome(id = "-1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "💥 Race Condition! Ordinary thread interleaving")
@Outcome(id = "-2", expect = Expect.ACCEPTABLE_INTERESTING, desc = "💥 Race Condition! Store-Load Reordering caused a return of stale values.")
@State
public class PossibleReorderingTest {

    private final PossibleReordering reordering = new PossibleReordering();

    @Actor
    public void reader(I_Result r) {
        try {
            PossibleReordering.IntPair pair = reordering.getPair();
            if(pair.a() == 0 && pair.b() == 0){
                r.r1 = -2;
                return;
            }
            if (pair.a() == 0 || pair.b() == 0) {
                r.r1 = -1;
                return;
            }
            r.r1 = 1;
        } catch (InterruptedException e) {}
    }
}
