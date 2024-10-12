package qa.retrofit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.http_clients.retrofit.services.CryptoCompareService;
import qa.http_clients.retrofit.RetrofitCallExecutor;
import qa.http_clients.retrofit.RetrofitManager;
import qa.utils.DateTimeUtils;
import retrofit2.Call;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CryptoCompareApiTest {

    private static CryptoCompareService apiService;

    @BeforeAll
    public static void setup() {
        RetrofitManager retrofitManager = new RetrofitManager("https://min-api.cryptocompare.com");
        apiService = retrofitManager.createService(CryptoCompareService.class);
    }

    @Test
    @DisplayName("Получение данных по свечам для пары ETHUSD за 2018 год")
    public void getHistoricalEthUsdDataFor2018Test() {
        long endTime = DateTimeUtils.getUnixTimestampForDateInUnixTimeMilliseconds("31-12-2018");
        Call<JsonObject> call = apiService.getHistoricalData("ETH", "USD", 365, endTime);
        JsonObject responseBody = RetrofitCallExecutor.executeCall(call);

        JsonObject dataObject = responseBody.getAsJsonObject("Data");
        JsonArray historicalData = dataObject.getAsJsonArray("Data");
        assertAll("Проверяем, что ответ содержит исторические данные",
                () -> assertNotNull(responseBody, "Response body is null"),
                () -> assertNotNull(historicalData, "Historical data is null"));

        JsonObject firstDayData = historicalData.get(0).getAsJsonObject();
        assertAll("Проверка первой записи",
                () -> assertEquals(1697155200, firstDayData.get("time").getAsLong(), "Timestamp does not match"),
                () -> assertEquals(1539.43, firstDayData.get("open").getAsDouble(), "Open price does not match"),
                () -> assertEquals(1552.12, firstDayData.get("close").getAsDouble(), "Close price does not match")
        );
    }

    @Test
    @DisplayName("Получение текущей цены пары BTCUSD")
    public void getCurrentPriceTest() {
        Call<JsonObject> call = apiService.getCurrentPrice("BTC", "USD");
        JsonObject body = RetrofitCallExecutor.executeCall(call);

        assertTrue(body.get("USD").getAsDouble() > 0, "Price must be greater than 0");
    }

    @Test
    @DisplayName("Получение исторических данных по паре BTCUSD")
    public void getHistoricalDataTest() {
        long toTimestampInSeconds = System.currentTimeMillis() / 1000;
        Call<JsonObject> call = apiService.getHistoricalData("BTC", "USD", 1, toTimestampInSeconds);
        JsonObject body = RetrofitCallExecutor.executeCall(call);

        assertNotNull(body, "Response body is null");
    }

    @Test
    @DisplayName("Получение текущей цены пары ETHUSD")
    public void getCurrentPriceEthTest() {
        Call<JsonObject> call = apiService.getCurrentPrice("ETH", "USD");
        JsonObject body = RetrofitCallExecutor.executeCall(call);

        assertNotNull(body, "Response body is null");
    }

    @Test
    @DisplayName("Получение текущей цены пары LTCUSD")
    public void getCurrentPriceLtcTest() {
        Call<JsonObject> call = apiService.getCurrentPrice("LTC", "USD");
        JsonObject body = RetrofitCallExecutor.executeCall(call);

        assertNotNull(body, "Response body is null");
    }

    @Test
    @DisplayName("Получение исторических данных по паре ETHUSD")
    public void getHistoricalDataEthTest() {
        long toTimestampInSeconds = System.currentTimeMillis() / 1000;
        Call<JsonObject> call = apiService.getHistoricalData("ETH", "USD", 1, toTimestampInSeconds);
        JsonObject body = RetrofitCallExecutor.executeCall(call);

        assertNotNull(body, "Response body is null");
    }
}
