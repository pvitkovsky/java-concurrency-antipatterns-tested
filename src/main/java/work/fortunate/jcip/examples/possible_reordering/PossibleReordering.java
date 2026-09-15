package work.fortunate.jcip.examples.possible_reordering;


/**
 * Adapted from JCIP listing 16.1
 */
public class PossibleReordering {
    public record IntPair(int a, int b){};
    static int x = 0, y = 0;
    static int a = 0, b = 0;

    public IntPair getPair() throws InterruptedException {
        Thread one = new Thread(new Runnable() {
            public void run() {
                a = 1;
                x = b;
            }
        });
        Thread two = new Thread(new Runnable() {
            public void run() {
                b = 1;
                y = a;
            }
        });
        one.start();
        two.start();
        one.join();
        two.join();
        return new IntPair(x, y);
    }
}
