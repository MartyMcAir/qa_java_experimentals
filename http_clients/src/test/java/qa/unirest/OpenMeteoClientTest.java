package qa.unirest;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenMeteoClientTest {

    private final OpenMeteoClient openMeteoClient = new OpenMeteoClient();

    @Test
    @DisplayName("Получение прогноза погоды по координатам")
    public void testGetWeatherForecast() {
        HttpResponse<JsonNode> response = openMeteoClient.getWeatherForecast(40.7128, -74.0060, "temperature_2m");
        assertEquals(200, response.getStatus(), "HTTP код должен быть 200");
        assertNotNull(response.getBody(), "Ответ не должен быть null");
        assertTrue(response.getBody().toString().contains("temperature_2m"), "Ответ должен содержать данные о температуре");
    }

    @Test
    @DisplayName("Получение погоды по координатам и параметрам влажности")
    public void testGetWeatherWithHumidity() {
        HttpResponse<JsonNode> response = openMeteoClient.getWeatherForecast(34.0522, -118.2437, "relative_humidity_2m");
        assertEquals(200, response.getStatus(), "HTTP код должен быть 200");
        assertNotNull(response.getBody(), "Ответ не должен быть null");
        assertTrue(response.getBody().toString().contains("relative_humidity_2m"), "Ответ должен содержать данные о влажности");
    }
}
