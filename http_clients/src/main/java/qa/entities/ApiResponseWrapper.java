package qa.entities;

import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ApiResponseWrapper {

    private final Response response;

    public ApiResponseWrapper(Response response) {
        this.response = response;
    }

    public int code() {
        return response.code();
    }

    public ResponseBody body() {
        return response.body();
    }

    public String bodyAsString() {
        try {
            return response.body() != null ? response.body().string() : null;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read response body", e);
        }
    }

    public boolean isSuccessful() {
        return response.isSuccessful();
    }

    public Map<String, List<String>> getHeaders() {
        return response.headers().toMultimap();
    }
}
