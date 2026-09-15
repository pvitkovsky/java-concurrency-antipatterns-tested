package work.fortunate.jcip.examples.escaping_object;

import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;

import java.util.UUID;

/**
 * Adapted from JCIP 3.7
 * A this reference gets published before the object is in consistent state.
 */
@NotThreadSafe
public class EscapingObject {
    public final UUID id;

    public EscapingObject(EscapingContext context) {
        context.activeInstance = this;
        this.id = new UUID(64l, 0l);
    }
}

