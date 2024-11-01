package qa.unirest;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;

public class FredClient {

    private static final String BASE_URL = "https://api.stlouisfed.org/fred";

    public HttpResponse<JsonNode> getSeries(String seriesId, String apiKey) {
        return Unirest.get(BASE_URL + "/series")
                .queryString("series_id", seriesId)
                .queryString("api_key", apiKey)
                .queryString("file_type", "json")
                .asJson();
    }

    public HttpResponse<JsonNode> getObservations(String seriesId, String apiKey) {
        return Unirest.get(BASE_URL + "/series/observations")
                .queryString("series_id", seriesId)
                .queryString("api_key", apiKey)
                .queryString("file_type", "json")
                .asJson();
    }

    public HttpResponse<JsonNode> getCategories(String apiKey) {
        return Unirest.get(BASE_URL + "/category")
                .queryString("category_id", 125) // Example category ID
                .queryString("api_key", apiKey)
                .queryString("file_type", "json")
                .asJson();
    }
}
