package qa.managers.okhttp;

import lombok.SneakyThrows;
import okhttp3.*;
import qa.entities.ResponseWrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OkhttpManager {
    private final OkHttpClient client = new OkHttpClient();

    public ResponseWrapper sendGetRequest(OkHttpRequestBuilder requestBuilder, int expectedCode) {
        return checkRequest(expectedCode, requestBuilder.method("GET").build());
    }

    public ResponseWrapper sendPostRequest(OkHttpRequestBuilder requestBuilder, String jsonBody, int expectedCode) {
        return checkRequest(expectedCode, requestBuilder.method("POST").jsonBody(jsonBody).build());
    }

    public ResponseWrapper sendPutRequest(OkHttpRequestBuilder requestBuilder, String jsonBody, int expectedCode) {
        return checkRequest(expectedCode, requestBuilder.method("PUT").jsonBody(jsonBody).build());
    }

    public ResponseWrapper sendDeleteRequest(OkHttpRequestBuilder requestBuilder, int expectedCode) {
        return checkRequest(expectedCode, requestBuilder.method("DELETE").build());
    }

    @SneakyThrows
    private ResponseWrapper checkRequest(int expectedCode, Request request) {
        try (Response response = client.newCall(request).execute()) {
            assertEquals(response.code(), expectedCode, "Unexpected response code: " + response.code());
            return new ResponseWrapper(response);
        }
    }
}
