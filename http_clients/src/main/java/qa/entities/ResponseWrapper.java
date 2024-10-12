package qa.entities;

import kong.unirest.core.json.JSONArray;
import kong.unirest.core.json.JSONException;
import kong.unirest.core.json.JSONObject;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ResponseWrapper {

    private final Response response;
    private final String cachedBody;

    public ResponseWrapper(Response response) {
        this.response = response;
        try {
            this.cachedBody = response.body() != null ? response.body().string() : null;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read response body", e);
        }
    }
    public int code() {
        return response.code();
    }

    public ResponseBody body() {
        return response.body();
    }

    public String bodyAsString() {
        return cachedBody;
    }

    public boolean isSuccessful() {
        return response.isSuccessful();
    }

    public Map<String, List<String>> getHeaders() {
        return response.headers().toMultimap();
    }

    private String getBodyOrThrow() {
        String body = bodyAsString();
        if (body == null) {
            throw new RuntimeException("Response body is null");
        }
        return body;
    }

    public JSONObject getJsonObject() {
        try {
            return new JSONObject(getBodyOrThrow());
        } catch (JSONException e) {
            throw new RuntimeException("Failed to parse response body as JSONObject", e);
        }
    }

    public JSONArray getJsonArray() {
        try {
            return new JSONArray(getBodyOrThrow());
        } catch (JSONException e) {
            throw new RuntimeException("Failed to parse response body as JSONArray", e);
        }
    }

    public boolean isJsonArray() {
        try {
            new JSONArray(getBodyOrThrow());
            return true;
        } catch (JSONException e) {
            return false;
        }
    }
}