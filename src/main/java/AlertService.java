public class AlertService {
    private final ConfigProvider config;
    private final Pager pager;
    private final HealthCheck healthCheck = new HealthCheck();

    public AlertService(ConfigProvider config, Pager pager) {
        this.config = config;
        this.pager = pager;
    }

    public Status check(String serviceName) {
        int latency = config.thresholdMs();          // значення з першої залежності
        Status status = healthCheck.status(latency); // правило з досліду 1
         pager.page(serviceName + ": " + status + " (" + latency + " мс)"); // друга залежність
        return status;
    }
}