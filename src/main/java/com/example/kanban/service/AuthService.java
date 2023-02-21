package com.example.kanban.service;


import com.example.kanban.model.UserLogin;
import com.example.kanban.model.AuthToken;

public interface AuthService {
    AuthToken authenticate(UserLogin userLogin);
}
