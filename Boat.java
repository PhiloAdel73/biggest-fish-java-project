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
