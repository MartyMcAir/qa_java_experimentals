package qa.unirest;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FredClientTest {

    private static FredClient fredClient;
    private static final String API_KEY = "your_api_key"; // Replace with your API key

    @BeforeAll
    public static void setup() {
        fredClient = new FredClient();
    }

    @Test
    @DisplayName("Получение данных по серии данных")
    public void testGetSeries() {
        HttpResponse<JsonNode> response = fredClient.getSeries("GDP", API_KEY);
        assertEquals(200, response.getStatus(), "HTTP код должен быть 200");
        assertNotNull(response.getBody(), "Тело ответа не должно быть null");
        assertTrue(response.getBody().getObject().has("seriess"), "Ответ должен содержать ключ 'seriess'");
    }

    @Test
    @DisplayName("Получение наблюдений для серии данных")
    public void testGetObservations() {
        HttpResponse<JsonNode> response = fredClient.getObservations("GDP", API_KEY);
        assertEquals(200, response.getStatus(), "HTTP код должен быть 200");
        assertNotNull(response.getBody(), "Тело ответа не должно быть null");
        assertTrue(response.getBody().getObject().has("observations"), "Ответ должен содержать ключ 'observations'");
    }

    @Test
    @DisplayName("Получение данных категории")
    public void testGetCategories() {
        HttpResponse<JsonNode> response = fredClient.getCategories(API_KEY);
        assertEquals(200, response.getStatus(), "HTTP код должен быть 200");
        assertNotNull(response.getBody(), "Тело ответа не должно быть null");
        assertTrue(response.getBody().getObject().has("categories"), "Ответ должен содержать ключ 'categories'");
    }
}
