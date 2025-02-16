package dev;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.javalin.UserController;
import io.javalin.Javalin;
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.protocol.HttpClientContext;
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
public class JavalinUserControllerTest {
    private static final String BASE_URL = "http://localhost:8080";
    private static final String USERS_ENDPOINT = "/api/users";
    private static CloseableHttpClient client;
    private static Javalin app;
    private static Long createdUserId;

    @BeforeAll
    static void setup() {
        // Запускаем Javalin перед тестами
        app = Javalin.create().start(8080);
        UserController userController = new UserController(new ObjectMapper());
        userController.registerRoutes(app);

        client = HttpClients.createDefault();
    }

    @Test
    @Order(1)
    void testCreateUser() throws IOException, ParseException, URISyntaxException {
        HttpPost request = new HttpPost(USERS_ENDPOINT);
        request.setEntity(new StringEntity("{\"name\":\"John Doe\",\"email\":\"john@example.com\"}"));
        request.setHeader("Content-Type", "application/json");

        try (CloseableHttpResponse response = (CloseableHttpResponse)
                client.executeOpen(HttpHost.create(BASE_URL), request, HttpClientContext.create())) {
            assertEquals(200, response.getCode());

            String responseBody = EntityUtils.toString(response.getEntity());
            createdUserId = extractUserId(responseBody);
            assertNotNull(createdUserId, "ID пользователя должен быть не null");
            System.out.println("✅ Пользователь создан с ID: " + createdUserId);
        }
    }

    @Test
    @Order(2)
    void testGetAllUsers() throws IOException, URISyntaxException {
        HttpGet request = new HttpGet(USERS_ENDPOINT);
        try (CloseableHttpResponse response = (CloseableHttpResponse)
                client.executeOpen(HttpHost.create(BASE_URL), request, HttpClientContext.create())) {
            assertEquals(200, response.getCode());
        }
    }

    @Test
    @Order(3)
    void testGetUserById() throws IOException, ParseException, URISyntaxException {
        assertNotNull(createdUserId, "ID пользователя должен быть задан перед тестом");

        HttpGet request = new HttpGet(USERS_ENDPOINT + "/" + createdUserId);
        try (CloseableHttpResponse response = (CloseableHttpResponse)
                client.executeOpen(HttpHost.create(BASE_URL), request, HttpClientContext.create())) {
            assertEquals(200, response.getCode());

            String responseBody = EntityUtils.toString(response.getEntity());
            assertTrue(responseBody.contains("John Doe"), "Ответ должен содержать имя пользователя");
            System.out.println("✅ Получен пользователь с ID: " + createdUserId);
        }
    }

    @Test
    @Order(4)
    void testDeleteUserById() throws IOException, URISyntaxException {
        assertNotNull(createdUserId, "ID пользователя должен быть задан перед тестом");

        HttpDelete request = new HttpDelete(USERS_ENDPOINT + "/" + createdUserId);
        try (CloseableHttpResponse response = (CloseableHttpResponse)
                client.executeOpen(HttpHost.create(BASE_URL), request, HttpClientContext.create())) {
            assertEquals(204, response.getCode());
            System.out.println("✅ Пользователь с ID " + createdUserId + " удален");
        }

    }

    @AfterAll
    static void tearDown() throws IOException {
        client.close();
        app.stop();
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
