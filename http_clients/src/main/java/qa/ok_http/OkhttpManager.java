package qa.ok_http;

import lombok.SneakyThrows;
import okhttp3.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OkhttpManager {
    private final OkHttpClient client = new OkHttpClient();

    public OkHttpResponseWrapper sendGetRequest(OkHttpRequestBuilder requestBuilder, int expectedCode) {
        return checkRequest(expectedCode, requestBuilder.method("GET").build());
    }

    public OkHttpResponseWrapper sendPostRequest(OkHttpRequestBuilder requestBuilder, String jsonBody, int expectedCode) {
        return checkRequest(expectedCode, requestBuilder.method("POST").jsonBody(jsonBody).build());
    }

    public OkHttpResponseWrapper sendPutRequest(OkHttpRequestBuilder requestBuilder, String jsonBody, int expectedCode) {
        return checkRequest(expectedCode, requestBuilder.method("PUT").jsonBody(jsonBody).build());
    }

    public OkHttpResponseWrapper sendDeleteRequest(OkHttpRequestBuilder requestBuilder, int expectedCode) {
        return checkRequest(expectedCode, requestBuilder.method("DELETE").build());
    }

    @SneakyThrows
    private OkHttpResponseWrapper checkRequest(int expectedCode, Request request) {
        try (Response response = client.newCall(request).execute()) {
            assertEquals(response.code(), expectedCode, "Unexpected response code: " + response.code());
            return new OkHttpResponseWrapper(response);
        }
    }
}
