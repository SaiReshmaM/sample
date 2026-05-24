package com.hackathon.leasemanagement.service;

import com.hackathon.leasemanagement.dto.LoginRequest;
import com.hackathon.leasemanagement.dto.RegisterRequest;
import com.hackathon.leasemanagement.entity.User;
import com.hackathon.leasemanagement.exception.BadRequestException;
import com.hackathon.leasemanagement.exception.ResourceNotFoundException;
import com.hackathon.leasemanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .role(request.getRole())
                .phoneNumber(request.getPhoneNumber())
                .build();

        return userRepository.save(user);
    }

    public User login(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getEmail()));
    }
}
