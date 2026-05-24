package com.hackathon.leasemanagement.controller;

import com.hackathon.leasemanagement.dto.LoginRequest;
import com.hackathon.leasemanagement.dto.RegisterRequest;
import com.hackathon.leasemanagement.dto.JwtResponse;
import com.hackathon.leasemanagement.entity.User;
import com.hackathon.leasemanagement.service.AuthService;
import com.hackathon.leasemanagement.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = authService.login(request);
        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new JwtResponse(token, user));
    }
}
