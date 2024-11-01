package qa.unirest;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class YahooFinanceClientTest {

    private final YahooFinanceClient yahooFinanceClient = new YahooFinanceClient();

    @Test
    @DisplayName("Получение данных по акции AAPL")
    public void testGetStockData() {
        HttpResponse<JsonNode> response = yahooFinanceClient.getStockData("MCD");
        assertEquals(200, response.getStatus(), "HTTP код должен быть 200");
        assertNotNull(response.getBody(), "Тело ответа не должно быть null");
    }

    @Test
    @DisplayName("Получение данных с query параметрами")
    public void testGetWithQueryParams() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("symbols", "AAPL,GOOG");
        HttpResponse<JsonNode> response = yahooFinanceClient.getWithQueryParams("", queryParams);
        assertEquals(200, response.getStatus(), "HTTP код должен быть 200");
        assertNotNull(response.getBody(), "Тело ответа не должно быть null");
    }

    @Test
    @DisplayName("Создание нового ресурса")
    public void testCreateResource() {
        String jsonBody = "{ \"exampleField\": \"exampleValue\" }";
        HttpResponse<JsonNode> response = yahooFinanceClient.createResource("/example", jsonBody);
        assertEquals(201, response.getStatus(), "HTTP код должен быть 201 для создания ресурса");
    }

    @Test
    @DisplayName("Обновление ресурса")
    public void testUpdateResource() {
        String jsonBody = "{ \"exampleField\": \"updatedValue\" }";
        HttpResponse<JsonNode> response = yahooFinanceClient.updateResource("/example", "123", jsonBody);
        assertEquals(200, response.getStatus(), "HTTP код должен быть 200 для успешного обновления ресурса");
    }

    @Test
    @DisplayName("Удаление ресурса")
    public void testDeleteResource() {
        HttpResponse<JsonNode> response = yahooFinanceClient.deleteResource("/example", "123");
        assertEquals(204, response.getStatus(), "HTTP код должен быть 204 для успешного удаления ресурса");
    }
}

