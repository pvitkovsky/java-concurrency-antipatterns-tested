package work.fortunate.jcip.examples.escaping_object;

import java.util.UUID;

public class EscapingObject {
    public final UUID id;

    public EscapingObject(EscapingContext context) {
        context.activeInstance = this;
        this.id = new UUID(64l, 0l);
    }
}

