package qa.ok_http;

import lombok.Builder;
import lombok.Getter;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import qa.ok_http.endpoints.ApiEndpoint;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

@Getter
@Builder
public class OkHttpRequestBuilder {

    private final MediaType jsonMediaType = MediaType.get("application/json; charset=utf-8");
    private ApiEndpoint endpoint;
    private Map<String, String> queryParams;
    private Map<String, String> headers;
    private String jsonBody;
    private String method;

    public OkHttpRequestBuilder method(String method) {
        this.method = method;
        return this;
    }

    public OkHttpRequestBuilder jsonBody(String jsonBody) {
        this.jsonBody = jsonBody;
        return this;
    }

    public Request build() {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(
                HttpUrl.parse(endpoint.getBaseUrl() + endpoint.getPath())).newBuilder();
        if (queryParams != null) queryParams.forEach(urlBuilder::addQueryParameter);
        Request.Builder requestBuilder = new Request.Builder().url(urlBuilder.build());
        if (headers != null) headers.forEach(requestBuilder::addHeader);

        BiConsumer<Request.Builder, RequestBody> setRequestBody = (builder, reqBody) -> builder.method(method.toUpperCase(),
                Objects.requireNonNullElseGet(reqBody, () -> RequestBody.create("", jsonMediaType)));

        switch (method.toUpperCase()) {
            case "POST":
            case "PUT":
                setRequestBody.accept(requestBuilder, RequestBody.create(jsonBody, jsonMediaType));
                break;
            case "DELETE":
                requestBuilder.delete();
                break;
            default:
                requestBuilder.get();
                break;
        }
        return requestBuilder.build();
    }
}