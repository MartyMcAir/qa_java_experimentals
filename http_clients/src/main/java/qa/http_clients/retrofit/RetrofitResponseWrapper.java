package qa.http_clients.retrofit;

import kong.unirest.core.json.JSONArray;
import kong.unirest.core.json.JSONObject;
import retrofit2.Response;

import java.util.List;
import java.util.Map;

public class RetrofitResponseWrapper<T> {

    private final Response<T> response;

    public RetrofitResponseWrapper(Response<T> response) {
        this.response = response;
    }

    public int code() {
        return response.code();
    }

    public T body() {
        return response.body();
    }

    public String bodyAsString() {
        if (response.body() == null) {
            throw new RuntimeException("Response body is null");
        }
        return response.body().toString();
    }

    public boolean isSuccessful() {
        return response.isSuccessful();
    }

    public Map<String, List<String>> getHeaders() {
        return response.headers().toMultimap();
    }

    public JSONObject getJsonObject() {
        try {
            return new JSONObject(bodyAsString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response body as JSONObject", e);
        }
    }

    public JSONArray getJsonArray() {
        try {
            return new JSONArray(bodyAsString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response body as JSONArray", e);
        }
    }
}