public class Main {
    static int best = 0;
    static int bestBy = 0;
    static int checks = 0;
    static MySemaphore mutex = new MySemaphore(1);

    public static void main(String[] args) {
        Boat[] boats = new Boat[6];
        for (int i = 0; i < 6; i++) {
            boats[i] = new Boat(i + 1);
            boats[i].start();
        }
        for (int i = 0; i < 6; i++) {
            try {
                boats[i].join();
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Best weight: " + best);
        System.out.println("Found by Boat number: " + bestBy);
        System.out.println("Checks made: " + checks);
    }
}
