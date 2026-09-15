package work.fortunate.jcip.util;

public class Blackhole {
    public static void consumeCPU(int tokens) {
        int volatileData = 0;
        for (int i = 0; i < tokens; i++) {
            volatileData += i;
        }
        System.out.println("Consumed loops count: " + volatileData);
    }
}
