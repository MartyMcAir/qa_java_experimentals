package qa.unirest;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;

import java.util.Map;

public class OpenMeteoClient {

    private final String baseUrl = "https://api.open-meteo.com/v1/forecast";

    public HttpResponse<JsonNode> getWeatherForecast(double latitude, double longitude, String parameters) {
        return Unirest.get(baseUrl)
                .queryString("latitude", latitude)
                .queryString("longitude", longitude)
                .queryString("hourly", parameters)
                .asJson();
    }

    public HttpResponse<JsonNode> getWeatherByCity(String city, Map<String, Object> queryParams) {
        return Unirest.get(baseUrl + "/city")
                .queryString("city", city)
                .queryString(queryParams)
                .asJson();
    }
}
