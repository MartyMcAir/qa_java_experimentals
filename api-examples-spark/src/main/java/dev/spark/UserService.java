package dev.spark;

import dev.model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class UserService {
    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public User createUser(User user) {
        user.setId(counter.getAndIncrement());
        users.put(user.getId(), user);
        return user;
    }

    public Optional<User> updateUser(Long id, User userDetails) {
        if (users.containsKey(id)) {
            User user = users.get(id);
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public boolean deleteUser(Long id) {
        return users.remove(id) != null;
    }
}
