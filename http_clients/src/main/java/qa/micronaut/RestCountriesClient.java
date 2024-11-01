package qa.micronaut;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.annotation.Client;

import java.util.List;
import java.util.Map;

@Client("https://restcountries.com/v3.1")
public interface RestCountriesClient {

    @Get("/name/{country}")
    HttpResponse<List<Map<String, Object>>> getCountryByName(String country);

    @Get("/all")
    HttpResponse<List<Map<String, Object>>> getAllCountries();

    @Get("/region/{region}")
    HttpResponse<List<Map<String, Object>>> getCountriesByRegion(@QueryValue String region);
}
