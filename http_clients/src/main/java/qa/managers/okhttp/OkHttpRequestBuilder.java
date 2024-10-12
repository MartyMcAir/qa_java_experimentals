package qa.managers.okhttp;

import lombok.Builder;
import lombok.Getter;
import okhttp3.HttpUrl;
import okhttp3.Request;
import qa.endpoints.CoinGeckoEndpoints;

import java.util.Map;

@Getter
@Builder
public class OkHttpRequestBuilder {

    private CoinGeckoEndpoints endpoint;
    private Map<String, String> queryParams;
    private Map<String, String> headers;

    // Метод для построения запроса
    public Request build(String baseUrl) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + endpoint.getPath()).newBuilder();

        if (queryParams != null) {
            queryParams.forEach(urlBuilder::addQueryParameter);
        }

        Request.Builder requestBuilder = new Request.Builder().url(urlBuilder.build());

        if (headers != null) {
            headers.forEach(requestBuilder::addHeader);
        }

        return requestBuilder.build();
    }
}