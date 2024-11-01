package qa.rest_assured;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Collections;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

public class RestAssuredManager {

    private final String baseUrl;

    public RestAssuredManager(String baseUrl) {
        this.baseUrl = baseUrl;
        RestAssured.baseURI = baseUrl;
    }

    public Response getRequest(String endpoint) {
        return RestAssured
                .given()
                .queryParams(Collections.emptyMap())
                .when()
                .get(endpoint)
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .response();
    }

    public Response getRocketById(String endpoint, String rocketId) {
        return RestAssured
                .given()
                .when()
                .get(endpoint, rocketId)
                .then()
                .extract()
                .response();
    }

    public static Response postRequest(String endpoint, Map<String, String> headers, String jsonBody) {
        return RestAssured.given()
                .headers(headers)
                .body(jsonBody)
                .when()
                .post(endpoint);
    }

    public static Response putRequest(String endpoint, Map<String, String> headers, String jsonBody) {
        return RestAssured.given()
                .headers(headers)
                .body(jsonBody)
                .when()
                .put(endpoint);
    }

    public static Response deleteRequest(String endpoint, Map<String, String> headers) {
        return RestAssured.given()
                .headers(headers)
                .when()
                .delete(endpoint);
    }
}
