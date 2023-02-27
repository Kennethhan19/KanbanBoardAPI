package com.example.kanban.service.Auth;


import com.example.kanban.model.User.UserLogin;
import com.example.kanban.model.AuthToken;

public interface AuthService {
    AuthToken authenticate(UserLogin userLogin);
}
