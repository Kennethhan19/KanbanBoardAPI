package com.example.kanban.service;

import com.example.kanban.config.JwtService;
import com.example.kanban.model.UserLogin;
import com.example.kanban.model.AuthToken;
import com.example.kanban.model.User;
import com.example.kanban.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthToken authenticate(UserLogin userLogin){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLogin.getEmail(),
                            userLogin.getPassword()
                    )
            );
            User user = userRepository.findByEmail(userLogin.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            var jwtToken = jwtService.generateToken(user);
            return AuthToken.builder().token(jwtToken).build();
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email/password");
        }
    }
}
