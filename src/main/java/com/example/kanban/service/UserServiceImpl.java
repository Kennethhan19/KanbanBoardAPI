package com.example.kanban.service;

import com.example.kanban.config.JwtService;
import com.example.kanban.model.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.kanban.model.User;
import com.example.kanban.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);


    @Override
    public AuthToken registerNewUser(User user){
        String password = user.getPassword();
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthToken.builder().token(jwtToken).build();
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    @Override
    public Optional<User> getUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public Optional<User> getUserByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public User updateUserInfo(Long id, User user){
        User tempUser = userRepository.findById(id).get();
        tempUser.setEmail(user.getEmail());
        tempUser.setUsername(user.getUsername());
        tempUser.setRole(user.getRole());
        tempUser.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(tempUser);
    }

    @Override
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
