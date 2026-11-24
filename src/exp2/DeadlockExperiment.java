package exp2;

public class DeadlockExperiment {
    // true  -> зависне (знімаємо дамп потоків)
    // false -> виправлена версія, програма завершується
    static final boolean UNSAFE = false;

    static void transfer(Cluster from, Cluster to) throws InterruptedException {
        if (UNSAFE) {
            Cluster.transferUnsafe(from, to);
        } else {
            Cluster.transferSafe(from, to);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Cluster a = new Cluster(1, 10);
        Cluster b = new Cluster(2, 10);

        Thread t1 = new Thread(() -> {
            try {
                transfer(a, b);   // a -> b
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                transfer(b, a);   // b -> a
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Готово: кластер 1 = " + a.getInstances()
                + ", кластер 2 = " + b.getInstances());
    }
}