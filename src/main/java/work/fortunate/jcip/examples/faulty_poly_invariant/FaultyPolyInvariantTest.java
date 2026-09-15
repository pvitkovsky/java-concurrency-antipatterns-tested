package work.fortunate.jcip.examples.faulty_poly_invariant;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

@JCStressTest
@Outcome(id = "1", expect = Expect.ACCEPTABLE, desc = "Invariant held.")
@Outcome(id = "-1", expect = Expect.FORBIDDEN, desc = "💥 Race Condition! Invariant lost.")
@State
public class FaultyPolyInvariantTest {

    private FaultyPolyInvariant polyInvariant = new FaultyPolyInvariant();

    @Actor
    public void setA(){
        try{
            polyInvariant.setLowerBound(10);
            polyInvariant.setUpperBound(20);
        } catch (Throwable t) {
            System.out.println(t.getMessage());
        }
    }

    @Actor
    public void setB(){
        try {
            polyInvariant.setLowerBound(1);
            polyInvariant.setUpperBound(2);
        } catch (Throwable t) {
            System.out.println(t.getMessage());
        }
    }

    @Arbiter
    public void checkResults(I_Result r){
        if(polyInvariant.isOK()){
            r.r1 = 1;
            return;
        }
        r.r1 = -1;
    }
}
