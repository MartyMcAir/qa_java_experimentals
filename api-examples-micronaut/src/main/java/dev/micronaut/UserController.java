package dev.micronaut;

import dev.model.User;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Get
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Get("/{id}")
    public Optional<User> getUserById(Long id) {
        return userService.getUserById(id);
    }

    @Post
    public User createUser(@Body User user) {
        return userService.createUser(user);
    }

    @Put("/{id}")
    public HttpResponse<User> updateUser(@PathVariable Long id, @Body User updatedUser) {
        Optional<User> user = userService.updateUser(id, updatedUser);
        return user.map(HttpResponse::ok)
                .orElseGet(HttpResponse::notFound);
    }

    @Delete("/{id}")
    public HttpResponse<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return HttpResponse.noContent();
        } else {
            return HttpResponse.notFound();
        }
    }

}
