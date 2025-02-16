package dev;

import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SparkUserControllerTest {
    private static final String BASE_URL = "http://localhost:8080";
    private static final String USERS_ENDPOINT = "/api/users";
    private static CloseableHttpClient client;
    private static Long createdUserId;

    @BeforeAll
    static void setup() throws InterruptedException {
        new Thread(() -> SparkApplication.main(new String[]{})).start();
        Thread.sleep(2000);
        client = HttpClients.createDefault();
    }

    @Test
    @Order(1)
    void testCreateUser() throws IOException, ParseException, URISyntaxException {
        HttpPost request = new HttpPost(USERS_ENDPOINT);
        request.setEntity(new StringEntity("{\"name\":\"John Doe\",\"email\":\"john@example.com\"}"));
        request.setHeader("Content-Type", "application/json");

        ClassicHttpResponse response = client.executeOpen(HttpHost.create(BASE_URL), request, HttpClientContext.create());
        assertEquals(200, response.getCode());

        String responseBody = EntityUtils.toString(response.getEntity());
        createdUserId = extractUserId(responseBody);
        assertNotNull(createdUserId, "ID пользователя должен быть не null");
        System.out.println("✅ Пользователь создан с ID: " + createdUserId);
    }

    @Test
    @Order(2)
    void testGetAllUsers() throws IOException, URISyntaxException {
        HttpGet request = new HttpGet(USERS_ENDPOINT);
        ClassicHttpResponse response = client.executeOpen(HttpHost.create(BASE_URL), request, HttpClientContext.create());
        assertEquals(200, response.getCode());
    }

    @Test
    @Order(3)
    void testGetUserById() throws IOException, ParseException, URISyntaxException {
        assertNotNull(createdUserId, "ID пользователя должен быть задан перед тестом");

        HttpGet request = new HttpGet(USERS_ENDPOINT + "/" + createdUserId);
        ClassicHttpResponse response = client.executeOpen(HttpHost.create(BASE_URL), request, HttpClientContext.create());
        assertEquals(200, response.getCode());

        String responseBody = EntityUtils.toString(response.getEntity());
        assertTrue(responseBody.contains("John Doe"), "Ответ должен содержать имя пользователя");
        System.out.println("✅ Получен пользователь с ID: " + createdUserId);
    }

    @Test
    @Order(4)
    void testUpdateUser() throws IOException, URISyntaxException, ParseException {
        assertNotNull(createdUserId, "ID пользователя должен быть задан перед тестом");

        HttpPut request = new HttpPut(USERS_ENDPOINT + "/" + createdUserId);
        request.setEntity(new StringEntity("{\"name\":\"John Doe Updated\",\"email\":\"john.doe.updated@example.com\"}"));
        request.setHeader("Content-Type", "application/json");

        ClassicHttpResponse response = client.executeOpen(HttpHost.create(BASE_URL), request, HttpClientContext.create());
        assertEquals(200, response.getCode());

        String responseBody = EntityUtils.toString(response.getEntity());
        assertTrue(responseBody.contains("John Doe Updated"), "Имя пользователя должно быть обновлено");
        assertTrue(responseBody.contains("john.doe.updated@example.com"), "Email пользователя должен быть обновлен");
        System.out.println("✅ Пользователь с ID " + createdUserId + " успешно обновлен");
    }


    @Test
    @Order(5)
    void testDeleteUserById() throws IOException, URISyntaxException {
        assertNotNull(createdUserId, "ID пользователя должен быть задан перед тестом");

        HttpDelete request = new HttpDelete(USERS_ENDPOINT + "/" + createdUserId);
        ClassicHttpResponse response = client.executeOpen(HttpHost.create(BASE_URL), request, HttpClientContext.create());
        assertEquals(204, response.getCode());
        System.out.println("✅ Пользователь с ID " + createdUserId + " удален");
    }

    @AfterAll
    static void tearDown() throws IOException {
        client.close();
    }

    private static Long extractUserId(String jsonResponse) {
        Pattern pattern = Pattern.compile("\"id\":(\\d+)");
        Matcher matcher = pattern.matcher(jsonResponse);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }
        return null;
    }
}