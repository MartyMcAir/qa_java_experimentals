package dev.spark;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.model.User;

import java.util.List;
import java.util.Optional;

import static spark.Spark.port;

import static spark.Spark.*;

public class UserController {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserController() {
        this.userService = new UserService();
        this.objectMapper = new ObjectMapper();
    }

    public void registerRoutes() {
        port(8080);

        get("/api/users", (req, res) -> {
            List<User> users = userService.getAllUsers();
            res.type("application/json");
            return objectMapper.writeValueAsString(users);
        });

        get("/api/users/:id", (req, res) -> {
            Long id = Long.parseLong(req.params("id"));
            Optional<User> user = userService.getUserById(id);
            if (user.isPresent()) {
                res.type("application/json");
                return objectMapper.writeValueAsString(user.get());
            } else {
                res.status(404);
                return "User not found";
            }
        });

        post("/api/users", (req, res) -> {
            User user = objectMapper.readValue(req.body(), User.class);
            User createdUser = userService.createUser(user);
            res.type("application/json");
            return objectMapper.writeValueAsString(createdUser);
        });

        put("/api/users/:id", (req, res) -> {
            Long id = Long.parseLong(req.params("id"));
            User userDetails = objectMapper.readValue(req.body(), User.class);
            Optional<User> updatedUser = userService.updateUser(id, userDetails);
            if (updatedUser.isPresent()) {
                res.type("application/json");
                return objectMapper.writeValueAsString(updatedUser.get());
            } else {
                res.status(404);
                return "User not found";
            }
        });

        delete("/api/users/:id", (req, res) -> {
            Long id = Long.parseLong(req.params("id"));
            if (userService.deleteUser(id)) {
                res.status(204);
                return "";
            } else {
                res.status(404);
                return "User not found";
            }
        });
    }
}
