package work.fortunate.jcip.examples.faulty_poly_invariant;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;

/**
 * Adapted from JCIP 4.10
 * To protect the invariant, the variables must be updated atomically.
 */
@NotThreadSafe
public class FaultyPolyInvariant {

    /** Invariant: lower <= upper */
    @GuardedBy("this")
    private volatile int lower = 0;
    @GuardedBy("this")
    private volatile int upper = 10;

    public void setLowerBound(int newLower) {
        if (newLower > upper) {
            throw new IllegalArgumentException("newLower > upper");
        }
        this.lower = newLower;
    }

    public void setUpperBound(int newUpper) {
        if (newUpper < lower) {
            throw new IllegalArgumentException("newUpper < lower");
        }
        this.upper = newUpper;
    }

    public boolean isOK() {
        return lower <= upper;
    }
}
