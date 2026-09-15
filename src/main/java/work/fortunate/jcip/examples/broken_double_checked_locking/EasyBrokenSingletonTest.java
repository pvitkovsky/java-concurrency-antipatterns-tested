package work.fortunate.jcip.examples.broken_double_checked_locking;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

/**
 * This class is at least expected to go down on ARM processors; x86
 * Intel and AMD x86/x64 processors implement a strongly ordered memory model called Total Store Order (TSO). At the physical hardware level, an x86 CPU core is fundamentally incapable of pushing a store instruction (like assigning instance) to the shared cache ahead of a prior store instruction (like setting first = 1)
 * https://stackoverflow.com/questions/18511152/are-x86-x64-architectures-resistant-to-unsafe-publication
 *
 * mvn clean verify
 * java -jar target/jcstress.jar -t EasyBrokenSingletonTest -f 10 -jvmArgs "-XX:-TieredCompilation -XX:+UnlockDiagnosticVMOptions -XX:+StressLCM -XX:+StressGCM"
 */
@JCStressTest
@Outcome(id = "0", expect = Expect.ACCEPTABLE, desc = "Not initialized yet.")
@Outcome(id = "1", expect = Expect.ACCEPTABLE, desc = "Fully initialized.")
@Outcome(id = "-1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "💥 Partially initialized object observed!")
@State
public class EasyBrokenSingletonTest {
    private EasyBrokenSingleton instance;

    @Actor
    public void writer() {
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    instance = new EasyBrokenSingleton();
                }
            }
        }
    }

    @Actor
    public void reader(I_Result r) {
        EasyBrokenSingleton h = instance;
        if (h == null) {
            r.r1 = 0;
        } else {
            if (h.first == 1 && h.second == 2) {
                r.r1 = 1;
            } else {
                r.r1 = -1;
            }
        }
    }
}
