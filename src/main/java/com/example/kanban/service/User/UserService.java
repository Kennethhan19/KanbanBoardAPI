package com.example.kanban.service.User;
import com.example.kanban.model.AuthToken;
import com.example.kanban.model.User.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerNewUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByUsername(String username);

    List<User> getUsersByBoards(Long boardId);

    User updateUserInfo(Long id, User user);

    void deleteUser(Long id);

}
