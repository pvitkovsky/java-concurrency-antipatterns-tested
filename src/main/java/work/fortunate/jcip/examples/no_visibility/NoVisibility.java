package work.fortunate.jcip.examples.no_visibility;

// TODO: spotbugs ignores this;
/**
 * adapted from JCIP 3.1
 */
public class NoVisibility {
    private boolean ready = false;
    private int number;

    private class ReaderThread extends Thread {
        public int res;
        public void run(){
            while (!ready) {
                Thread.yield();
            }
            res = number;
        }
    }
    public int test() throws InterruptedException {
        var t = new ReaderThread();
        t.start();
        // Simply returns t.res without waiting for anything: 0
        Thread.sleep(200);
        number = 42;
        ready = true;
        // simulates Thread.yield: t acquires Number;
         Thread.sleep(200);
        return t.res;
    }

    public static void main(String[] args) throws InterruptedException {
        NoVisibility noVisibility = new NoVisibility();
        System.out.println("Test "  + noVisibility.test());
        System.out.println("Number " + noVisibility.number);
    }
}
