package qa.micronaut;

import io.micronaut.http.HttpResponse;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@MicronautTest
public class RestCountriesClientTest {
// https://restcountries.com/

    @Inject
    private RestCountriesClient restCountriesClient;

    @Test
    @DisplayName("Получение и вывод топ стран по количеству населения")
    public void testGetTop5CountriesByPopulation() {
        int amountOfCountry = 35;
        HttpResponse<List<Map<String, Object>>> response = restCountriesClient.getAllCountries();
        assertNotNull(response.body(), "Response body is null");

        // Сортируем страны по населению и берем топ-5
        List<Map<String, Object>> topCountries = response.body().stream()
                .sorted((c1, c2) -> {
                    Long population1 = ((Number) c1.get("population")).longValue();
                    Long population2 = ((Number) c2.get("population")).longValue();
                    return population2.compareTo(population1);
                }).limit(amountOfCountry).toList();

        assertEquals(amountOfCountry, topCountries.size(), "Должно быть ровно 5 стран в топе");

        AtomicInteger counter = new AtomicInteger(1);
        topCountries.forEach(country -> {
            String countryName = (String) ((Map<String, Object>) country.get("name")).get("common");
            Long population = ((Number) country.get("population")).longValue();
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            String formattedPopulation = numberFormat.format(population);
            System.out.printf("%-3s | Страна: %-15s Население: %15s%n", counter.getAndIncrement(), countryName, formattedPopulation);
        });
    }

    @Test
    @DisplayName("Получение информации о стране по имени")
    public void testGetCountryByName() {
        var response = restCountriesClient.getCountryByName("France");
        assertEquals(200, response.getStatus().getCode());
        assertNotNull(response.body(), "Response body is null");
        assertFalse(response.body().isEmpty(), "Country data is empty");
        assertEquals("France", ((Map<String, Object>) response.body().get(0).get("name")).get("common"), "Country name does not match");
    }

    @Test
    @DisplayName("Получение списка всех стран")
    public void testGetAllCountries() {
        var response = restCountriesClient.getAllCountries();
        assertEquals(200, response.getStatus().getCode());
        assertNotNull(response.body(), "Response body is null");
        assertTrue(response.body().size() > 0, "Countries list should not be empty");
    }

    @Test
    @DisplayName("Получение стран по региону")
    public void testGetCountriesByRegion() {
        HttpResponse<List<Map<String, Object>>> response = restCountriesClient.getCountriesByRegion("Europe");
        assertEquals(200, response.getStatus().getCode());
        assertNotNull(response.body(), "Response body is null");
        assertTrue(response.body().size() > 0, "Countries in region should not be empty");

        assertTrue(response.body().stream().anyMatch(country -> country.get("region").equals("Europe")),
                "Region does not match 'Europe'");
    }

}
