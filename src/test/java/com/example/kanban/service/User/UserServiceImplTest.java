package com.example.kanban.service.User;

import com.example.kanban.model.User.Role;
import com.example.kanban.model.User.User;
import com.example.kanban.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void registerNewUserTest() {
        // Given
        Long expectedId = 1L;
        String username = "John";
        String email = "John123@gmail.com";
        String password = "abcde12345";
        Role role = Role.ADMIN;

        User userTest = User.builder()
                .email(email)
                .role(role)
                .username(username)
                .password(password)
                .build();

        //when
        when(userRepository.save(any(User.class))).thenAnswer(invocation->{
            User savedUser = invocation.getArgument(0);
            savedUser.setId(expectedId);
            return savedUser;
        });

        User registeredUser = userService.registerNewUser(userTest);

        verify(userRepository).save(userTest);
        assertEquals(registeredUser.getEmail(), email);
        assertEquals(registeredUser.getUsername(), username);
        assertEquals(registeredUser.getRole(), role);
        assertEquals(registeredUser.getId(), expectedId);
    }

    @Test
    void getAllUsersTest() {
        //when
        userService.getAllUsers();
        // then
        verify(userRepository).findAll();
    }

    @Test
    @Disabled
    void getUserById() {
        Long Id = 10L;

    }

    @Test
    @Disabled
    void getUserByEmail() {
    }

    @Test
    @Disabled
    void getUserByUsername() {
    }

    @Test
    @Disabled
    void getUsersByBoards() {
    }

    @Test
    @Disabled
    void updateUserInfo() {
    }

    @Test
    @Disabled
    void deleteUser() {
    }
}