package work.fortunate.jcip.examples.broken_double_checked_locking;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

/**
 * This class is not expected to go down - the singleton state persists across individual runs because BrokenSingleton instance is static
 *
 * sdk use java 21.0.12+1.1-tem
 * mvn clean verify
 * java -jar target/jcstress.jar -t BrokenSingletonTest -f 10 -v
 * */
@JCStressTest
@Outcome(id = "0", expect = Expect.ACCEPTABLE, desc = "Not initialized yet.")
@Outcome(id = "1", expect = Expect.ACCEPTABLE, desc = "Fully initialized.")
@Outcome(id = "-1", expect = Expect.FORBIDDEN, desc = "💥 Partially initialized object observed!")
@State
public class BrokenSingletonTest {
    @Actor
    public void writer() {
        try {
            BrokenSingleton.getInstance();
        } catch (InterruptedException ex){
            System.out.println(ex.getMessage());
        }

    }

    @Actor
    public void reader(I_Result r) {
        try {
            BrokenSingleton h = BrokenSingleton.getInstance();
            if (h.first() == 1) {
                r.r1 = 0;
            } else {
                r.r1 = -1;
            }
        } catch (InterruptedException ex){
            System.out.println(ex.getMessage());
        }

    }
}
