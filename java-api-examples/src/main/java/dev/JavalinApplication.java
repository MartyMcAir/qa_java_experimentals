package dev;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.javalin.controller.UserController;
import io.javalin.Javalin;

public class JavalinApplication {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        Javalin app = Javalin.create().start(8080);

        UserController userController = new UserController(objectMapper);
        userController.registerRoutes(app);

        System.out.println("Javalin API запущен на http://localhost:8080");
    }
}
