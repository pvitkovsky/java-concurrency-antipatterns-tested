package work.fortunate.jcip.examples.incomplete_locking;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;
import org.jspecify.annotations.Nullable;

/**
 * Non-JCIP example.
 * A state variable is accessible around its lock.
 * Interesting that nor ErrorProne nor SpotBugs find this.
 */
@NotThreadSafe
public class IncompleteLocking {

    private final Object lock = new Object();

    private StringBuilder ledger = new StringBuilder("0");

    public void record(@Nullable String entry) {
        synchronized (lock) {
            ledger.append('|').append(entry);
        }
    }

    /** Intentionally reads a guarded field without holding the lock. */
    public String peekUnsafe() {
        return ledger.toString();
    }
}
