import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlertServiceTest {

    @Mock
    ConfigProvider config;   // стаб: лише віддає значення
    @Mock
    Pager pager;             // мок: перевіряємо виклик

    @InjectMocks
    AlertService service;

    @Test
    void pagesWithComputedStatus() {
        // Given
        when(config.thresholdMs()).thenReturn(500);
        // When
        Status result = service.check("auth-service");
        // Then
        assertEquals(Status.SLOW, result);
        verify(pager).page("auth-service: SLOW (500 мс)");
    }
}