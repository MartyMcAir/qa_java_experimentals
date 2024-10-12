package qa.managers.okhttp;

import lombok.SneakyThrows;
import okhttp3.*;
import qa.entities.ApiResponseWrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OkhttpCoinGeckoManager {
    private final OkHttpClient client = new OkHttpClient();
    private final String BASE_URL = "https://api.coingecko.com/api/v3";

    @SneakyThrows
    public ApiResponseWrapper sendRequest(OkHttpRequestBuilder requestBuilder, int expectedCode) {
        Request request = requestBuilder.build(BASE_URL);

        try (Response response = client.newCall(request).execute()) {
            assertEquals(response.code(), expectedCode, "Unexpected response code: " + response.code());
            return new ApiResponseWrapper(response);
        }
    }
    // ----------------------------------------------------------------------

    @SneakyThrows
    public Response sendGetRequest(String url) {
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .build();

        return client.newCall(request).execute();
    }

    @SneakyThrows
    public Response sendPostRequest(String url, String jsonBody) {
        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .post(body)
                .build();

        return client.newCall(request).execute();
    }

    @SneakyThrows
    public Response sendPutRequest(String url, String jsonBody) {
        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .put(body)
                .build();

        return client.newCall(request).execute();
    }

    @SneakyThrows
    public Response sendDeleteRequest(String url) {
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .delete()
                .build();

        return client.newCall(request).execute();
    }
}
