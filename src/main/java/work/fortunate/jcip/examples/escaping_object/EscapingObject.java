package work.fortunate.jcip.examples.escaping_object;

import net.jcip.annotations.ThreadSafe;

import java.util.UUID;

@ThreadSafe
public class EscapingObject {
    public final UUID id;

    public EscapingObject(EscapingContext context) {
        context.activeInstance = this;
        this.id = new UUID(64l, 0l);
    }
}

