package qa.micronaut;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import io.micronaut.http.HttpResponse;

import java.util.List;
import java.util.Map;

@MicronautTest
public class CoinGeckoClientTest {

    @Inject
    private CoinGeckoClient coinGeckoClient;

    @Test
    @DisplayName("Получение текущей цены Bitcoin")
    public void testGetCurrentPrice() {
        HttpResponse<Map<String, Map<String, Double>>> response = coinGeckoClient.getCurrentPrice("bitcoin", "usd");
        assertEquals(200, response.getStatus().getCode());
        assertNotNull(response.body());
        Double price = response.body().get("bitcoin").get("usd");
        assertTrue(price > 0, "Price must be greater than 0");
    }

    @Test
    @DisplayName("Получение рыночных данных для Bitcoin")
    public void testGetCoinMarketData() {
        HttpResponse<Map<String, Object>> response = coinGeckoClient.getCoinMarketData("bitcoin");
        assertEquals(200, response.getStatus().getCode());
        assertNotNull(response.body());
        assertEquals("bitcoin", response.body().get("id"), "ID should be bitcoin");
    }

    @Test
    @DisplayName("Получение списка поддерживаемых валют")
    public void testGetSupportedVsCurrencies() {
        HttpResponse<List<String>> response = coinGeckoClient.getSupportedVsCurrencies();
        assertEquals(200, response.getStatus().getCode());
        assertNotNull(response.body());
        assertTrue(response.body().contains("usd"), "Response should contain 'usd'");
    }

    @Test
    @DisplayName("Получение данных по капитализации Ethereum")
    public void testGetMarketCapitalizationForEthereum() {
        HttpResponse<Map<String, Object>> response = coinGeckoClient.getCoinMarketData("ethereum");
        assertEquals(200, response.getStatus().getCode());
        assertNotNull(response.body());
        Map<String, Object> marketData = (Map<String, Object>) response.body().get("market_data");
        assertNotNull(marketData);
        assertTrue(marketData.containsKey("market_cap"), "Market data should contain 'market_cap'");
    }
}
