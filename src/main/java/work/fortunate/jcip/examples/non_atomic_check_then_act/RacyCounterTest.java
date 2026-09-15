package work.fortunate.jcip.examples.non_atomic_check_then_act;


import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

/**
 * mvn clean verify
 * java -jar target/jcstress.jar -t RacyCounterTest -f 10 -v
 */
@JCStressTest
@Outcome(id = "2", expect = Expect.ACCEPTABLE, desc = "Both updates succeeded.")
@Outcome(id = "1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "💥 Race Condition! Lost update captured.")
@State
public class RacyCounterTest {

    private final RacyCounter counter = new RacyCounter();

    @Actor
    public void thread1() {
        counter.increment();
    }

    @Actor
    public void thread2() {
        counter.increment();
    }

    @Arbiter
    public void checkResults(I_Result r) {
        r.r1 = counter.getCount();
    }
}
