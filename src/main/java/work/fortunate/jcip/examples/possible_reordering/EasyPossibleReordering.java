package work.fortunate.jcip.examples.possible_reordering;


import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;

/**
 * Adapted from JCIP listing 16.1
 */
@NotThreadSafe
public class EasyPossibleReordering {
    public record IntPair(int a, int b){};
    private int a = 0, b = 0;
    private int x = 0, y = 0;

    public void runA(){
        a = 1;
        x = b;
    }

    public void runB(){
        b = 1;
        y = a;
    }

    public IntPair peek(){
        return new IntPair(x, y);
    }
}
