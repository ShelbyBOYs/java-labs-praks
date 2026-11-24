package exp1;

public class CounterExperiment {
    static final int N = 6;        // потоків
    static final int M = 80_000;   // викликів на потік

    public static void main(String[] args) throws InterruptedException {
        EventCounter counter = new EventCounter();
        Thread[] threads = new Thread[N];

        for (int i = 0; i < N; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < M; j++) {
                    counter.add();
                }
            });
        }

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }

        System.out.println(counter.getCount() + " / " + (N * M));
    }
}
