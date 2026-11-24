public class HealthCheck {

    public Status status(int latencyMs) {
        if (latencyMs < 0) {
            throw new IllegalArgumentException("latencyMs must be >= 0: " + latencyMs);
        }
        if (latencyMs <= 200) {
            return Status.OK;
        }
        if (latencyMs <= 1000) {
            return Status.SLOW;
        }
        return Status.DOWN;
    }
}