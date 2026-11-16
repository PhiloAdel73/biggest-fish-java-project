class MySemaphore {
    private int permits;

    public MySemaphore(int permits) {
        this.permits = permits;
    }

    public synchronized void acquire() {
        while (permits == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        permits--;
    }

    public synchronized void release() {
        permits++;
        notifyAll();
    }
}

class Boat extends Thread {
    private int id;

    public Boat(int id) {
        this.id = id;
    }

    public void run() {
        for (int j = 1; j <= 5; j++) {
            int v = (id * 41 + j * 13) % 89 + 1;

            Main.mutex.acquire();
            Main.checks++;
            if (v > Main.best) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
                Main.best = v;
                Main.bestBy = id;
            }
            Main.mutex.release();
        }
    }
}

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
