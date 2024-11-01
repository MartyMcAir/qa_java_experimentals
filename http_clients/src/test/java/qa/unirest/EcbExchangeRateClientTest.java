package qa.unirest;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public class EcbExchangeRateClientTest {

    @Inject
    private EcbExchangeRateClient ecbExchangeRateClient;

    @Test
    @DisplayName("Получение текущих обменных курсов для базовой валюты EUR")
    public void testGetLatestExchangeRates() {
        Map<String, Object> response = ecbExchangeRateClient.getLatestExchangeRates("EUR");
        assertNotNull(response, "Ответ не должен быть null");
        assertTrue(response.containsKey("rates"), "Ответ должен содержать ключ 'rates'");
    }

    @Test
    @DisplayName("Получение текущих обменных курсов с ограничением по валютам (EUR и GBP)")
    public void testGetExchangeRatesWithSymbols() {
        Map<String, Object> response = ecbExchangeRateClient.getExchangeRatesWithSymbols("USD", "EUR,GBP");
        assertNotNull(response, "Ответ не должен быть null");
        assertTrue(response.containsKey("rates"), "Ответ должен содержать ключ 'rates'");
        assertTrue(((Map<String, Object>) response.get("rates")).containsKey("EUR"), "Должен содержать EUR");
        assertTrue(((Map<String, Object>) response.get("rates")).containsKey("GBP"), "Должен содержать GBP");
    }

    @Test
    @DisplayName("Получение исторических обменных курсов")
    public void testGetHistoricalExchangeRates() {
        Map<String, Object> response = ecbExchangeRateClient.getHistoricalExchangeRates(
                "2023-01-01", "2023-01-10", "USD", "EUR,GBP");
        assertNotNull(response, "Ответ не должен быть null");
        assertTrue(response.containsKey("rates"), "Ответ должен содержать ключ 'rates'");
    }

    @Test
    @DisplayName("Получение текущих курсов для нескольких базовых валют")
    public void testGetExchangeRatesForMultipleBases() {
        List<String> baseCurrencies = List.of("USD", "EUR");
        for (String base : baseCurrencies) {
            Map<String, Object> response = ecbExchangeRateClient.getExchangeRatesForBase(base);
            assertNotNull(response, "Ответ не должен быть null для валюты " + base);
            assertTrue(response.containsKey("rates"), "Ответ должен содержать ключ 'rates' для валюты " + base);
        }
    }
}
