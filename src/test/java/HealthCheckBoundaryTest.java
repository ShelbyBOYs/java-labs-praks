import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class HealthCheckBoundaryTest {

    private final HealthCheck check = new HealthCheck();

    @ParameterizedTest
    @CsvSource({
            "0,OK",
            "200,OK",
            "201,SLOW",
            "1000,SLOW",
            "1001,DOWN"
    })
    void statusOnBoundaries(int latency, Status expected) {
        assertEquals(expected, check.status(latency));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100})
    void negativeLatencyThrows(int latency) {
        assertThrows(IllegalArgumentException.class, () -> check.status(latency));
    }
}