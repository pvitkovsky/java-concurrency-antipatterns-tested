package work.fortunate.jcip.examples.broken_double_checked_locking;

import work.fortunate.jcip.util.Blackhole;

public class ARMRewiringBrokenSingleton {
    public int first;
    public int second;

    // A hook allowing the test state to grab the instance early
    public static ARMRewiringBrokenSingletonTest activeTestContext;

    public ARMRewiringBrokenSingleton() {
        // ⚠️ TRICKING THE JIT: We leak the partially constructed object reference 
        // to the test state BEFORE initializing the integer fields.
        if (activeTestContext != null) {
            activeTestContext.setInstanceManually(this);
        }

        // Simulate some minor work so the window stays open long enough 
        // for the reader thread to catch it.
        Blackhole.consumeCPU(10);

        this.first = 1;
        this.second = 2;
    }
}
