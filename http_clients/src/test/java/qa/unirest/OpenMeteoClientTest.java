package qa.unirest;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenMeteoClientTest {
// https://open-meteo.com/en/docs

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

    @ParameterizedTest
    @CsvSource({
            "55.7558, 37.6176, Moscow",
            "40.7128, -74.0060, New York",
            "38.7223, -9.1393, Lisbon",
            "22.3193, 114.1694, Hong Kong"
    })
    @DisplayName("Получение прогноза погоды для разных городов")
    public void testGetWeatherForecastForCities(double latitude, double longitude, String cityName) {
        HttpResponse<JsonNode> response = openMeteoClient.getWeatherForecast(latitude, longitude, "temperature_2m");
        assertEquals(200, response.getStatus(), "HTTP код должен быть 200 для " + cityName);
        assertNotNull(response.getBody(), "Ответ не должен быть null для " + cityName);
        assertTrue(response.getBody().toString().contains("temperature_2m"), "Ответ должен содержать данные о температуре для " + cityName);
        System.out.printf("Погода в %s успешно получена: %s%n", cityName, response.getBody().toPrettyString());
    }

    @Test
    void testGetHourlyWeatherForecast() {
        double latitude = 40.7128; // Нью-Йорк
        double longitude = -74.0060;
        HttpResponse<JsonNode> response = openMeteoClient.getHourlyWeatherForecast(latitude, longitude);

        assertEquals(200, response.getStatus(), "HTTP код должен быть 200");
        assertTrue(response.getBody().getObject().has("hourly"), "Ответ должен содержать данные о почасовом прогнозе");
        System.out.printf("Почасовой прогноз погоды для Нью-Йорка: %s%n", response.getBody().toPrettyString());
    }

    @Test
    public void testGetCurrentWeather() {
        HttpResponse<JsonNode> response = openMeteoClient.getCurrentWeather(55.7558, 37.6176); // Москва
        assertEquals(200, response.getStatus());
        assertNotNull(response.getBody(), "Response body should not be null");
        System.out.println("Current weather in Moscow: " + response.getBody().toString());
    }

    @Test
    public void testGetDailyWeatherForecast() {
        HttpResponse<JsonNode> response = openMeteoClient.getDailyWeatherForecast(40.7128, -74.0060); // Нью-Йорк
        assertEquals(200, response.getStatus());
        assertNotNull(response.getBody(), "Response body should not be null");
        System.out.println("Daily weather forecast in New York: " + response.getBody().toString());
    }

    @Test
    public void testGetWindForecast() {
        HttpResponse<JsonNode> response = openMeteoClient.getWindForecast(38.7223, -9.1393); // Лиссабон
        assertEquals(200, response.getStatus());
        assertNotNull(response.getBody(), "Response body should not be null");
        System.out.println("Wind forecast in Lisbon: " + response.getBody().toString());
    }

    @Test
    public void testGetHumidityForecast() {
        HttpResponse<JsonNode> response = openMeteoClient.getHumidityForecast(22.3193, 114.1694); // Гонконг
        assertEquals(200, response.getStatus());
        assertNotNull(response.getBody(), "Response body should not be null");
        System.out.println("Humidity forecast in Hong Kong: " + response.getBody().toString());
    }

    @Test
    public void testGetCurrentWeatherInMultipleCities() {
        double[][] cities = {
                {55.7558, 37.6176}, // Москва
                {40.7128, -74.0060}, // Нью-Йорк
                {38.7223, -9.1393}, // Лиссабон
                {22.3193, 114.1694} // Гонконг
        };

        for (double[] city : cities) {
            HttpResponse<JsonNode> response = openMeteoClient.getCurrentWeather(city[0], city[1]);
            assertEquals(200, response.getStatus());
            assertNotNull(response.getBody(), "Response body should not be null");
            System.out.printf("Current weather at coordinates (%.4f, %.4f): %s%n", city[0], city[1], response.getBody().toString());
        }
    }
}
