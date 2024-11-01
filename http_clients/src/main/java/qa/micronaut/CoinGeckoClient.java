package qa.micronaut;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Client("https://api.coingecko.com/api/v3")
public interface CoinGeckoClient {

    @Get("/simple/price")
    HttpResponse<Map<String, Map<String, Double>>> getCurrentPrice(
            @QueryValue("ids") String cryptoId,
            @QueryValue("vs_currencies") String currency);

    @Get("/coins/{id}")
    HttpResponse<Map<String, Object>> getCoinMarketData(@PathVariable String id);

    @Get("/coins/{id}/history")
    HttpResponse<Map<String, Object>> getHistoricalData(
            @PathVariable String id,
            @QueryValue("date") @NonNull String date);

    @Get("/simple/supported_vs_currencies")
    HttpResponse<List<String>> getSupportedVsCurrencies();
}
