package qa.unirest;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

import java.util.List;
import java.util.Map;

@Client("https://api.exchangeratesapi.io")
public interface EcbExchangeRateClient {

    @Get("/latest")
    Map<String, Object> getLatestExchangeRates(@QueryValue("base") String baseCurrency);

    @Get("/history")
    Map<String, Object> getHistoricalExchangeRates(
            @QueryValue("start_at") String startDate,
            @QueryValue("end_at") String endDate,
            @QueryValue("base") String baseCurrency,
            @QueryValue("symbols") String symbols);

    @Get("/latest")
    Map<String, Object> getExchangeRatesWithSymbols(
            @QueryValue("base") String baseCurrency,
            @QueryValue("symbols") String symbols);

    @Get("/latest")
    Map<String, Object> getExchangeRatesForMultipleBases(
            @QueryValue("base") List<String> baseCurrencies);

    @Get("/latest")
    Map<String, Object> getExchangeRatesForBase(@QueryValue("base") String baseCurrency);

}


