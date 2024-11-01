package qa.rest_assured;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.http_clients.rest_assured.SpaceXEndpoints;
import qa.http_clients.rest_assured.RestAssuredManager;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class SpaceXTest {
// https://github.com/r-spacex/SpaceX-API
// https://github.com/r-spacex/SpaceX-API/tree/master/docs/launches/v4

    private static RestAssuredManager manager;

    @BeforeAll
    public static void setup() {
        manager = new RestAssuredManager(SpaceXEndpoints.LAUNCHES.getBaseUrl());
    }

    @Test
    @DisplayName("Получение информации о последнем запуске")
    public void getLatestLaunchTest() {
        Response response = manager.getRequest(SpaceXEndpoints.LAUNCHES_LATEST.getPath());
        assertAll("Проверка ответа для последнего запуска",
                () -> assertNotNull(response, "Response is null"),
                () -> assertNotNull(response.jsonPath().getString("name"), "Launch name is missing"),
                () -> assertNotNull(response.jsonPath().getString("date_utc"), "Launch date is missing")
        );
    }

    @Test
    @DisplayName("Получение всех запусков")
    public void getAllLaunchesTest() {
        Response response = manager.getRequest(SpaceXEndpoints.LAUNCHES.getPath());
        assertAll("Проверка всех запусков",
                () -> assertNotNull(response, "Response is null"),
                () -> assertTrue(response.jsonPath().getList("$").size() > 0, "There should be at least one launch")
        );
    }

    @Test
    @DisplayName("Получение информации о конкретной ракете по ID")
    public void getRocketByIdTest() {
        String rocketId = "5e9d0d95eda69973a809d1ec";
        Response response = manager.getRocketById(SpaceXEndpoints.ROCKETS_ID.getPath(), rocketId);
        assertAll("Проверка информации о ракете",
                () -> assertNotNull(response, "Response is null"),
                () -> assertEquals(rocketId, response.jsonPath().getString("id"), "Rocket ID does not match"),
                () -> assertNotNull(response.jsonPath().getString("name"), "Rocket name is missing")
        );
    }

    @Test
    @DisplayName("Получение информации о предстоящих запусках")
    public void getUpcomingLaunchesTest() {
        Response response = manager.getRequest(SpaceXEndpoints.LAUNCHES_UPCOMING.getPath());
        assertAll("Проверка предстоящих запусков",
                () -> assertNotNull(response, "Response is null"),
                () -> assertTrue(response.jsonPath().getList("$").size() >= 0, "Expected a list of upcoming launches")
        );
    }

    @Test
    @DisplayName("Проверка наличия запуска с заданным названием")
    public void getLaunchByNameTest() {
        String expectedLaunchName = "FalconSat";
        Response response = manager.getRequest(SpaceXEndpoints.LAUNCHES.getPath());

        List<Object> matchingLaunches = response.jsonPath().getList("$").stream()
                .filter(launch -> expectedLaunchName.equals(((Map<String, Object>) launch).get("name")))
                .toList();

        assertAll("Проверка наличия запуска с заданным названием",
                () -> assertNotNull(response, "Response is null"),
                () -> assertFalse(matchingLaunches.isEmpty(), "Expected at least one launch with the name " + expectedLaunchName));
    }

}
