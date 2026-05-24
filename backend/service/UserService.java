package com.hackathon.leasemanagement.service;

import com.hackathon.leasemanagement.dto.CreateUserRequest;
import com.hackathon.leasemanagement.entity.User;
import com.hackathon.leasemanagement.enums.Role;
import com.hackathon.leasemanagement.exception.BadRequestException;
import com.hackathon.leasemanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(CreateUserRequest request) {
        // Optional: Check if email exists
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .role(request.getRole())
                .phoneNumber(request.getPhoneNumber())
                .build();
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new com.hackathon.leasemanagement.exception.ResourceNotFoundException("User not found with id: " + id));
    }
}
