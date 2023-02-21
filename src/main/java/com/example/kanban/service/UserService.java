package com.example.kanban.service;
import com.example.kanban.model.AuthToken;
import com.example.kanban.model.Task;
import com.example.kanban.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    AuthToken registerNewUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByUsername(String username);

    User updateUserInfo(Long id, User user);

    void deleteUser(Long id);

}
