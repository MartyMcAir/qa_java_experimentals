package qa.unirest;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;

import java.util.Map;

public class YahooFinanceClient {

    private static final String BASE_URL = "https://query1.finance.yahoo.com/v7/finance/quote";

    public HttpResponse<JsonNode> getStockData(String symbol) {
        return Unirest.get(BASE_URL)
                .queryString("symbols", symbol)
                .header("accept", "application/json")
                .asJson();
    }

    public HttpResponse<JsonNode> createResource(String endpoint, String jsonBody) {
        return Unirest.post(BASE_URL + endpoint)
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .asJson();
    }

    public HttpResponse<JsonNode> updateResource(String endpoint, String resourceId, String jsonBody) {
        return Unirest.put(BASE_URL + endpoint + "/" + resourceId)
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .asJson();
    }

    public HttpResponse<JsonNode> deleteResource(String endpoint, String resourceId) {
        return Unirest.delete(BASE_URL + endpoint + "/" + resourceId)
                .asJson();
    }

    public HttpResponse<JsonNode> getWithQueryParams(String endpoint, Map<String, Object> queryParams) {
        return Unirest.get(BASE_URL + endpoint)
                .queryString(queryParams)
                .asJson();
    }
}
