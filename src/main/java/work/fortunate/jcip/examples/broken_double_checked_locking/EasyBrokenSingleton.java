package work.fortunate.jcip.examples.broken_double_checked_locking;

public class EasyBrokenSingleton {
    public int first;
    public int second;

    public EasyBrokenSingleton() {
        this.first = 1;
        this.second = 2;
    }
}
