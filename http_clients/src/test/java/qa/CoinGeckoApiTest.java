package qa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.endpoints.CoinGeckoEndpoints;
import qa.entities.ApiResponseWrapper;
import qa.managers.okhttp.OkHttpRequestBuilder;
import qa.managers.okhttp.OkhttpCoinGeckoManager;

import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CoinGeckoApiTest {

    private OkhttpCoinGeckoManager httpManager;

    @BeforeEach
    public void setup() {
        httpManager = new OkhttpCoinGeckoManager();
    }

    @Test
    @DisplayName("Получение информации об исторической цене криптовалюты: Bitcoin на дату 30-12-2022")
    public void getCoinHistoryData() {
        OkHttpRequestBuilder builder = OkHttpRequestBuilder.builder()
                .endpoint(CoinGeckoEndpoints.COIN_HISTORY)
                .queryParams(Map.of("date", "30-12-2022")) // формат DD-MM-YYYY
                .build();
        ApiResponseWrapper response = httpManager.sendRequest(builder, HTTP_OK);

        assertNotNull(response.bodyAsString());
    }

    @Test
    @DisplayName("Получение списка поддерживаемых валют")
    public void getSupportedCurrencies() {
        OkHttpRequestBuilder builder = OkHttpRequestBuilder.builder()
                .endpoint(CoinGeckoEndpoints.SUPPORTED_CURRENCIES)
                .build();
        ApiResponseWrapper response = httpManager.sendRequest(builder, HTTP_OK);

        assertNotNull(response.bodyAsString());
    }

    @Test
    @DisplayName("Получение информации о криптовалюте по ID: Ethereum")
    public void getCoinDataById() {
        OkHttpRequestBuilder builder = OkHttpRequestBuilder.builder()
                .endpoint(CoinGeckoEndpoints.COIN_DATA)
                .queryParams(Map.of("id", "ethereum"))
                .build();
        ApiResponseWrapper response = httpManager.sendRequest(builder, HTTP_OK);

        assertNotNull(response.bodyAsString());
    }

    @Test
    @DisplayName("Получение списка всех криптовалют с указанием ранга")
    public void getAllCoinsList() {
        OkHttpRequestBuilder builder = OkHttpRequestBuilder.builder()
                .endpoint(CoinGeckoEndpoints.ALL_COINS)
                .build();
        ApiResponseWrapper response = httpManager.sendRequest(builder, HTTP_OK);

        assertNotNull(response.bodyAsString());
    }
}

