package dev.javalin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.model.User;
import dev.javalin.service.UserService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.Optional;

public class UserController {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserController(ObjectMapper objectMapper) {
        this.userService = new UserService();
        this.objectMapper = objectMapper;
    }

    public void registerRoutes(Javalin app) {
        app.get("/api/users", this::getAllUsers);
        app.get("/api/users/{id}", this::getUserById);
        app.post("/api/users", this::createUser);
        app.put("/api/users/{id}", this::updateUser);
        app.delete("/api/users/{id}", this::deleteUser);
    }

    private void getAllUsers(Context ctx) {
        ctx.json(userService.getAllUsers());
    }

    private void getUserById(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        Optional<User> user = userService.getUserById(id);
        user.ifPresentOrElse(ctx::json, () -> ctx.status(404));
    }

    private void createUser(Context ctx) {
        try {
            User user = objectMapper.readValue(ctx.body(), User.class);
            ctx.json(userService.createUser(user));
        } catch (Exception e) {
            ctx.status(400);
        }
    }

    private void updateUser(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        try {
            User user = objectMapper.readValue(ctx.body(), User.class);
            Optional<User> updatedUser = userService.updateUser(id, user);
            updatedUser.ifPresentOrElse(ctx::json, () -> ctx.status(404));
        } catch (Exception e) {
            ctx.status(400);
        }
    }

    private void deleteUser(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        if (userService.deleteUser(id)) {
            ctx.status(204);
        } else {
            ctx.status(404);
        }
    }
}
