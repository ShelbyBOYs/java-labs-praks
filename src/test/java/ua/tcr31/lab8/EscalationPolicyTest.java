package ua.tcr31.lab8;

import org.junit.jupiter.api.Test;
import ua.vendor.incidents.Action;
import ua.vendor.incidents.EscalationPolicy;
import ua.vendor.incidents.Severity;
import ua.vendor.incidents.Vendor;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class EscalationPolicyTest {

    private static final ZoneId KYIV = ZoneId.of("Europe/Kyiv");
    private static final Instant NOW = Instant.parse("2026-03-10T12:00:00Z");
    private final Clock clock = Clock.fixed(NOW, KYIV);
    private final EscalationPolicy policy =
            Vendor.build(Integer.getInteger("build", 1), clock);

    // CRITICAL
    @Test void criticalUnacknowledgedPages() {
        assertEquals(Action.PAGE_ON_CALL, policy.decide(Severity.CRITICAL, NOW, false));
    }
    @Test void criticalAcknowledgedPages() {
        assertEquals(Action.PAGE_ON_CALL, policy.decide(Severity.CRITICAL, NOW.minusSeconds(3600), true));
    }
    @Test void criticalJustOpenedPages() {
        assertEquals(Action.PAGE_ON_CALL, policy.decide(Severity.CRITICAL, NOW, false));
    }

    // LOW, MEDIUM
    @Test void lowUnacknowledgedOldLogsOnly() {
        assertEquals(Action.LOG_ONLY, policy.decide(Severity.LOW, NOW.minusSeconds(7200), false));
    }
    @Test void lowAcknowledgedLogsOnly() {
        assertEquals(Action.LOG_ONLY, policy.decide(Severity.LOW, NOW.minusSeconds(7200), true));
    }
    @Test void mediumUnacknowledgedOldLogsOnly() {
        assertEquals(Action.LOG_ONLY, policy.decide(Severity.MEDIUM, NOW.minusSeconds(7200), false));
    }
    @Test void mediumAcknowledgedLogsOnly() {
        assertEquals(Action.LOG_ONLY, policy.decide(Severity.MEDIUM, NOW.minusSeconds(7200), true));
    }

    // HIGH, підтверджений
    @Test void highAcknowledgedOldLogsOnly() {
        assertEquals(Action.LOG_ONLY, policy.decide(Severity.HIGH, NOW.minusSeconds(7200), true));
    }
    @Test void highAcknowledgedJustOpenedLogsOnly() {
        assertEquals(Action.LOG_ONLY, policy.decide(Severity.HIGH, NOW, true));
    }

    // HIGH, непідтверджений, межа 15 хв
    @Test void highUnacknowledgedJustOpenedWaits() {
        assertEquals(Action.WAIT, policy.decide(Severity.HIGH, NOW, false));
    }
    @Test void highUnacknowledged14m59sWaits() {
        assertEquals(Action.WAIT, policy.decide(Severity.HIGH, NOW.minusSeconds(14 * 60 + 59), false));
    }
    @Test void highUnacknowledgedExactly15mPages() {
        assertEquals(Action.PAGE_ON_CALL, policy.decide(Severity.HIGH, NOW.minusSeconds(15 * 60), false));
    }
    @Test void highUnacknowledged15m01sPages() {
        assertEquals(Action.PAGE_ON_CALL, policy.decide(Severity.HIGH, NOW.minusSeconds(15 * 60 + 1), false));
    }
    @Test void highUnacknowledgedOldPages() {
        assertEquals(Action.PAGE_ON_CALL, policy.decide(Severity.HIGH, NOW.minusSeconds(86400), false));
    }

    // Некоректні аргументи
    @Test void nullSeverityThrows() {
        assertThrows(IllegalArgumentException.class, () -> policy.decide(null, NOW, false));
    }
    @Test void nullOpenedAtThrows() {
        assertThrows(IllegalArgumentException.class, () -> policy.decide(Severity.HIGH, null, false));
    }
    @Test void openedInFutureBy1sThrows() {
        assertThrows(IllegalArgumentException.class, () -> policy.decide(Severity.HIGH, NOW.plusSeconds(1), false));
    }
    @Test void openedInFutureBy1hThrows() {
        assertThrows(IllegalArgumentException.class, () -> policy.decide(Severity.HIGH, NOW.plusSeconds(3600), false));
    }
    @Test void openedInFutureThrowsForLowToo() {
        assertThrows(IllegalArgumentException.class, () -> policy.decide(Severity.LOW, NOW.plusSeconds(1), true));
    }
    @Test void openedInFutureThrowsForCriticalToo() {
        assertThrows(IllegalArgumentException.class, () -> policy.decide(Severity.CRITICAL, NOW.plusSeconds(1), false));
    }
}