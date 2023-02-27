package com.example.kanban.controller;

import com.example.kanban.model.User.UserLogin;
import com.example.kanban.model.AuthToken;
import com.example.kanban.model.User.User;
import com.example.kanban.service.Auth.AuthService;
import com.example.kanban.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;


@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthToken> registerUser(@RequestBody User user) {
        Optional<User> userEmail = userService.getUserByEmail(user.getEmail());
        Optional<User> userName = userService.getUserByUsername(user.getUsername());
        String password = user.getPassword();

        if (userEmail.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
        }

        if (userName.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already in use");
        }

        if(password.length() < 8){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long");
        }
        return ResponseEntity.ok(userService.registerNewUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthToken> login(@RequestBody UserLogin userLogin){
        return ResponseEntity.ok(authService.authenticate(userLogin));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User ID not found")));
    }

    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "User Email not found")));
    }

    @GetMapping("/username")
    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not found")));
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<User>> getUsersByBoards(@PathVariable Long boardId){
        return ResponseEntity.ok(userService.getUsersByBoards(boardId));
    }

    @PutMapping("/update")
    public ResponseEntity<User>  updateUser(@RequestBody User user,@AuthenticationPrincipal User principal){
        Optional<User> userEmail = userService.getUserByEmail(user.getEmail());
        Optional<User> userName = userService.getUserByUsername(user.getUsername());
        String password = user.getPassword();

        if (userEmail.isPresent() && userEmail.get().getId() != principal.getId()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
        }

        if (userName.isPresent() && userName.get().getId() != principal.getId() ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already in use");
        }

        if(password.length() < 8){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long");
        }

        return ResponseEntity.ok(userService.updateUserInfo(principal.getId(), user));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User>  updateAnyUser(@PathVariable Long id, @RequestBody User user) {
            Optional<User> userEmail = userService.getUserByEmail(user.getEmail());
            Optional<User> userName = userService.getUserByUsername(user.getUsername());
            String password = user.getPassword();

            if (userEmail.isPresent() && userEmail.get().getId() != id){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
            }

            if (userName.isPresent() && userName.get().getId() != id){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already in use");
            }

            if(password.length() < 8){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long");
            }

            return ResponseEntity.ok(userService.updateUserInfo(id, user));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteAnyUserById(@PathVariable Long id) {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteCurrentUser(@AuthenticationPrincipal User principal) {
            userService.deleteUser(principal.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
