package com.bankapp.auth_service.controller;

import com.bankapp.auth_service.entity.User;
import com.bankapp.auth_service.model.*;
import com.bankapp.auth_service.service.UserService;
import com.bankapp.auth_service.util.JWTUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JWTUtil jwtUtil;

    public AuthController(UserService userService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Validate user credentials
        User user = userService.validateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (user == null) {
            AuthServiceResponse authServiceResponse = new AuthServiceResponse(false, "Invalid username or password", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authServiceResponse);
        }

        // Generate JWT token
        String token = jwtUtil.generateJwtToken(user.getUsername());

        // Return token
        AuthServiceResponse authServiceResponse = new AuthServiceResponse(true, "Login successful", new LoginResponse(token));
        return ResponseEntity.status(HttpStatus.OK).body(authServiceResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            User user = userService.registerUser(registerRequest);
            AuthServiceResponse authServiceResponse = new AuthServiceResponse(true, "User registered successfully", new RegisterResponse(user.getId()));
            return ResponseEntity.status(HttpStatus.CREATED).body(authServiceResponse);
        } catch (RuntimeException e) {
            AuthServiceResponse authServiceResponse = new AuthServiceResponse(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authServiceResponse);
        }
    }
}
