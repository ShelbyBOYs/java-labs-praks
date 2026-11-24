package exp2;

public class Cluster {
    private final int id;
    private int instances;

    public Cluster(int id, int instances) {
        this.id = id;
        this.instances = instances;
    }

    public int getId() {
        return id;
    }

    public int getInstances() {
        return instances;
    }

    // З deadlock: спочатку from, потім to
    public static void transferUnsafe(Cluster from, Cluster to) throws InterruptedException {
        synchronized (from) {
            Thread.sleep(50);
            synchronized (to) {
                from.instances--;
                to.instances++;
            }
        }
    }

    // Виправлено: монітори за зростанням id
    public static void transferSafe(Cluster from, Cluster to) throws InterruptedException {
        Cluster first = from.id < to.id ? from : to;
        Cluster second = from.id < to.id ? to : from;

        synchronized (first) {
            Thread.sleep(50);
            synchronized (second) {
                from.instances--;
                to.instances++;
            }
        }
    }
}