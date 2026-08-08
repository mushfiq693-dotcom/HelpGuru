package com.helpguru.auth.service;

import com.helpguru.auth.dto.AuthResponse;
import com.helpguru.auth.dto.LoginRequest;
import com.helpguru.auth.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest);
}
