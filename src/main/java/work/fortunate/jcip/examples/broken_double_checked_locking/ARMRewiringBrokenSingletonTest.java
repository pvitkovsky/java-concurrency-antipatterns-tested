package work.fortunate.jcip.examples.broken_double_checked_locking;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

/**
 * This horrible contraption actually shows initialization race;
 */
@JCStressTest
@Outcome(id = "0", expect = Expect.ACCEPTABLE, desc = "Not initialized yet.")
@Outcome(id = "1", expect = Expect.ACCEPTABLE, desc = "Fully initialized.")
@Outcome(id = "-1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "💥 Partially initialized object observed!")
@State
public class ARMRewiringBrokenSingletonTest {
    private ARMRewiringBrokenSingleton instance;

    // Package-private setter for our constructor leak trick
    void setInstanceManually(ARMRewiringBrokenSingleton leaked) {
        this.instance = leaked;
    }

    @Actor
    public void writer() {
        // Set the context hook so the constructor leaks back to this state
        ARMRewiringBrokenSingleton.activeTestContext = this;

        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    new ARMRewiringBrokenSingleton(); // Allocation triggers the constructor leak
                }
            }
        }
    }

    @Actor
    public void reader(I_Result r) {
        ARMRewiringBrokenSingleton h = instance;
        if (h == null) {
            r.r1 = 0;
        } else {
            if (h.first == 1 && h.second == 2) {
                r.r1 = 1;
            } else {
                r.r1 = -1; // 💥 Will trigger because reader catches the leaked reference!
            }
        }
    }
}

