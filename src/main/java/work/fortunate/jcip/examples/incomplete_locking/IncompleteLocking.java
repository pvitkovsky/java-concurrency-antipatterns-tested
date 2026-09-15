package work.fortunate.jcip.examples.incomplete_locking;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Incomplete locking
 * spotbugs ignores this;
 */
@ThreadSafe // For spotbugs
public class IncompleteLocking {

    private final Object lock = new Object();

    @GuardedBy("lock")
    private StringBuilder ledger = new StringBuilder("0");

    public void record(String entry) {
        synchronized (lock) {
            ledger.append('|').append(entry);
        }
    }

    /** Intentionally reads a guarded field without holding the lock. */
    public String peekUnsafe() {
        return ledger.toString();
    }
}
