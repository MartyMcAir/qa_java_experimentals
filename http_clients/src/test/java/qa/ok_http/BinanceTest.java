package qa.ok_http;

import kong.unirest.core.json.JSONArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.ok_http.endpoints.BinanceEndpoints;
import qa.utils.DateTimeUtils;

import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BinanceTest {
// https://developers.binance.com/docs/binance-spot-api-docs/faqs/market_data_only

    private static OkhttpManager manager;

    @BeforeAll
    public static void setup() {
        manager = new OkhttpManager();
    }

    @Test
    @DisplayName("Получение данных по свечам для пары BTCUSDT за определенную дату")
    public void getHistoricalKlineDataForDateTest() {
        long startTime = DateTimeUtils.getUnixTimestampForDateInUnixTimeMilliseconds("01-01-2023");
        long endTimeOfTheDay = startTime + (24 * 60 * 60 * 1000) - 1;
        OkHttpRequestBuilder builder = OkHttpRequestBuilder.builder()
                .endpoint(BinanceEndpoints.KLINE_DATA)
                .queryParams(Map.of(
                        "symbol", "BTCUSDT",
                        "interval", "1d",
                        "startTime", String.valueOf(startTime),
                        "endTime", String.valueOf(endTimeOfTheDay)))
                .build();

        OkHttpResponseWrapper response = manager.sendGetRequest(builder, HTTP_OK);
        JSONArray firstKline = response.getJsonArray().getJSONArray(0);

        assertAll("Проверка цены для пары BTCUSDT за определенную дату",
                () -> assertEquals(1672531200000L, firstKline.getLong(0), "Start time does not match"),
                () -> assertEquals("16541.77000000", firstKline.getString(1), "Open price does not match"),
                () -> assertEquals("16616.75000000", firstKline.getString(4), "Close price does not match"));
    }

    @Test
    @DisplayName("Получение текущей цены пары BTCUSDT")
    public void getCurrentPriceTest() {
        OkHttpRequestBuilder builder = OkHttpRequestBuilder.builder()
                .endpoint(BinanceEndpoints.TICKER_PRICE)
                .queryParams(Map.of("symbol", "BTCUSDT"))
                .build();

        OkHttpResponseWrapper response = manager.sendGetRequest(builder, HTTP_OK);
        assertNotNull(response.body());
    }

    @Test
    @DisplayName("Получение информации об обмене (Binance Exchange Info)")
    public void getExchangeInfoTest() {
        OkHttpRequestBuilder builder = OkHttpRequestBuilder.builder()
                .endpoint(BinanceEndpoints.EXCHANGE_INFO)
                .build();

        OkHttpResponseWrapper response = manager.sendGetRequest(builder, HTTP_OK);
        assertNotNull(response.body());
    }

    @Test
    @DisplayName("Получение данных по свечам для пары ETHUSDT")
    public void getKlineDataTest() {
        OkHttpRequestBuilder builder = OkHttpRequestBuilder.builder()
                .endpoint(BinanceEndpoints.KLINE_DATA)
                .queryParams(Map.of("symbol", "ETHUSDT", "interval", "1h", "limit", "5"))
                .build();

        OkHttpResponseWrapper response = manager.sendGetRequest(builder, HTTP_OK);
        assertNotNull(response.body());
    }
}