import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HealthCheckTest {

    private final HealthCheck check = new HealthCheck();

    @Test
    void okForNormalLatency() {
        // Given
        int latency = 50;
        // When
        Status result = check.status(latency);
        // Then
        assertEquals(Status.OK, result);
    }

    @Test
    void negativeLatencyThrows() {
        // Given
        int latency = -1;
        // When + Then
        assertThrows(IllegalArgumentException.class, () -> check.status(latency));
    }

    @Test
    void slowForMiddleLatency() {
        // Given
        int latency = 500;
        // When
        Status result = check.status(latency);
        // Then
        assertEquals(Status.SLOW, result);
    }
}