package com.example.kanban.service.User;

import com.example.kanban.model.Board.Board;
import com.example.kanban.model.User.Role;
import com.example.kanban.model.User.User;
import com.example.kanban.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @Mock
    private BCryptPasswordEncoder passwordEncryptorMock;

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
        // Given
        String username1 = "John";
        String email1 = "John123@gmail.com";
        String password1 = "abcde12345";
        Role role1 = Role.ADMIN;

        User userTest1 = User.builder()
                .email(email1)
                .role(role1)
                .username(username1)
                .password(password1)
                .build();

        // Given
        String username2 = "Tom";
        String email2 = "tom123@gmail.com";
        String password2 = "abcde12345";
        Role role2 = Role.USER;

        User userTest2 = User.builder()
                .email(email2)
                .role(role2)
                .username(username2)
                .password(password2)
                .build();

        List<User> testUsers = Arrays.asList(userTest1,userTest2);
        //when
        when(userService.getAllUsers()).thenReturn(testUsers);
        // then
        List<User> users = userService.getAllUsers();
        verify(userRepository).findAll();
        assertEquals(users.get(0).getUsername(), username1);
        assertEquals(users.get(0).getEmail(), email1);
        assertEquals(users.get(1).getUsername(), username2);
        assertEquals(users.get(1).getEmail(), email2);
    }

    @Test
    void getUserById() {
        // Given
        Long expectedId = 1L;
        String username = "John";
        String email = "John123@gmail.com";
        String password = "abcde12345";
        Role role = Role.ADMIN;

        User userTest = User.builder()
                .id(expectedId)
                .email(email)
                .role(role)
                .username(username)
                .password(password)
                .build();

        //when
        when(userRepository.findById(expectedId)).thenReturn(Optional.of(userTest));

        //then
        Optional<User> testUser = userService.getUserById(1L);
        verify(userRepository).findById(1L);
        assertEquals(testUser.get().getId(), 1L);
    }

    @Test
    void getUserByEmail() {
        // Given
        Long expectedId = 1L;
        String username = "John";
        String email = "John123@gmail.com";
        String password = "abcde12345";
        Role role = Role.ADMIN;

        User userTest = User.builder()
                .id(expectedId)
                .email(email)
                .role(role)
                .username(username)
                .password(password)
                .build();

        //when
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userTest));

        //then
        Optional<User> testUser = userService.getUserByEmail(email);
        verify(userRepository).findByEmail(email);
        assertEquals(testUser.get().getEmail(), email);

    }

    @Test
    void getUserByUsername() {
        // Given
        Long expectedId = 1L;
        String username = "John";
        String email = "John123@gmail.com";
        String password = "abcde12345";
        Role role = Role.ADMIN;

        User userTest = User.builder()
                .id(expectedId)
                .email(email)
                .role(role)
                .username(username)
                .password(password)
                .build();

        //when
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userTest));

        //then
        Optional<User> testUser = userService.getUserByUsername(username);
        verify(userRepository).findByUsername(username);
        assertEquals(testUser.get().getUsername(), username);
    }

    @Test
    void getUsersByBoards() {
        // Given
        Long expectedId = 1L;
        String username = "John";
        String email = "John123@gmail.com";
        String password = "abcde12345";
        Role role = Role.ADMIN;
        Board board = Board.builder().id(10L).name("testBoard").build();
        List<Board> listBoard = Arrays.asList(board);
        User userTest = User.builder()
                .id(expectedId)
                .email(email)
                .role(role)
                .username(username)
                .password(password)
                .boards(listBoard)
                .build();
        List<User> listUser = Arrays.asList(userTest);
        board.setUsers(listUser);

        //when
        when(userRepository.findByBoards_Id(10L)).thenReturn(listUser);

        //then
        List<User> testBoard = userService.getUsersByBoards(10L);
        verify(userRepository).findByBoards_Id(10L);
        assertEquals(testBoard, listUser);
    }

    @Test
    void updateUserInfo() {
        // Given
        Long id = 10L;
        String username = "John";
        String email = "John123@gmail.com";
        String password = "abcde12345";
        Role role = Role.ADMIN;

        User userTest = User.builder()
                .id(id)
                .email(email)
                .role(role)
                .username(username)
                .password(password)
                .build();

        String newUsername = "James";
        String newEmail = "James123@gmail.com";
        String newPassword = "mockedPassword123";
        Role newRole = Role.ADMIN;

        User newUser = User.builder()
                .id(id)
                .email(newEmail)
                .role(newRole)
                .username(newUsername)
                .password(newPassword)
                .build();

        //when
        when(userRepository.findById(id)).thenReturn(Optional.of(userTest));
        when(passwordEncryptorMock.encode(any(String.class))).thenReturn("mockedPassword123");
        when(userRepository.save(newUser)).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setPassword(passwordEncryptorMock.encode(savedUser.getPassword()));
            return savedUser;
        });


        //then
        User updatedUser = userService.updateUserInfo(id, newUser);
        verify(userRepository).findById(id);
        verify(userRepository).save(newUser);
        assertEquals(newUser, updatedUser);
    }

    @Test
    void deleteUser() {
        // Given
        Long id = 10L;

        // When
        userService.deleteUser(id);

        // Then
        verify(userRepository).deleteById(id);
    }
}