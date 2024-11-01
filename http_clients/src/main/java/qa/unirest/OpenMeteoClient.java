package qa.unirest;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;

import java.util.Map;

public class OpenMeteoClient {

    private final String BASE_URL = "https://api.open-meteo.com/v1/forecast";

    public HttpResponse<JsonNode> getWeatherForecast(double latitude, double longitude, String parameters) {
        return Unirest.get(BASE_URL)
                .queryString("latitude", latitude)
                .queryString("longitude", longitude)
                .queryString("hourly", parameters)
                .asJson();
    }

    public HttpResponse<JsonNode> getHourlyWeatherForecast(double latitude, double longitude) {
        return Unirest.get(BASE_URL)
                .queryString("latitude", latitude)
                .queryString("longitude", longitude)
                .queryString("hourly", "temperature_2m,precipitation")
                .asJson();
    }

    // Получение текущей погоды
    public HttpResponse<JsonNode> getCurrentWeather(double latitude, double longitude) {
        return Unirest.get(BASE_URL)
                .queryString("latitude", latitude)
                .queryString("longitude", longitude)
                .queryString("current_weather", true)
                .asJson();
    }

    // Получение прогноза погоды на несколько дней
    public HttpResponse<JsonNode> getDailyWeatherForecast(double latitude, double longitude) {
        return Unirest.get(BASE_URL)
                .queryString("latitude", latitude)
                .queryString("longitude", longitude)
                .queryString("daily", "temperature_2m_min,temperature_2m_max,precipitation_sum")
                .queryString("timezone", "auto")
                .asJson();
    }

    // Получение прогноза ветра
    public HttpResponse<JsonNode> getWindForecast(double latitude, double longitude) {
        return Unirest.get(BASE_URL)
                .queryString("latitude", latitude)
                .queryString("longitude", longitude)
                .queryString("hourly", "windspeed_10m,winddirection_10m")
                .asJson();
    }

    // Получение данных о влажности
    public HttpResponse<JsonNode> getHumidityForecast(double latitude, double longitude) {
        return Unirest.get(BASE_URL)
                .queryString("latitude", latitude)
                .queryString("longitude", longitude)
                .queryString("hourly", "relativehumidity_2m")
                .asJson();
    }
}
