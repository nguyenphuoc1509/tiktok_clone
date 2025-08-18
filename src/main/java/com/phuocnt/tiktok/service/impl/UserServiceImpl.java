package com.phuocnt.tiktok.service.impl;

import com.phuocnt.tiktok.dto.request.UserRequest;
import com.phuocnt.tiktok.dto.request.UserUpdateRequest;
import com.phuocnt.tiktok.dto.response.UserResponse;
import com.phuocnt.tiktok.entity.User;
import com.phuocnt.tiktok.repository.UserRepository;
import com.phuocnt.tiktok.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest req) {
        if (repo.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("Username already used");
        }
        if (repo.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already used");
        }

        User u = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .profilePictureUrl(req.getProfilePictureUrl())
                .bio(req.getBio())
                .isCreator(Boolean.TRUE.equals(req.getIsCreator()))
                .build();

        return toResponse(repo.save(u));
    }

    @Override
    public List<UserResponse> getUsers() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public UserResponse getUser(UUID userId) {
        User u = repo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        return toResponse(u);
    }

    @Override
    public UserResponse updateUser(UUID userId, UserUpdateRequest req) {
        User u = repo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        if (req.getUsername() != null && !req.getUsername().equals(u.getUsername())) {
            if (repo.existsByUsername(req.getUsername())) throw new IllegalArgumentException("Username already used");
            u.setUsername(req.getUsername());
        }

        if (req.getEmail() != null && !req.getEmail().equals(u.getEmail())) {
            if (repo.existsByEmail(req.getEmail())) throw new IllegalArgumentException("Email already used");
            u.setEmail(req.getEmail());
        }

        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            u.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        }

        if (req.getProfilePictureUrl() != null) u.setProfilePictureUrl(req.getProfilePictureUrl());
        if (req.getBio() != null) u.setBio(req.getBio());
        if (req.getIsCreator() != null) u.setIsCreator(req.getIsCreator());

        // ví dụ cập nhật lastLoginAt khi có logic đăng nhập riêng
        // u.setLastLoginAt(Instant.now());

        return toResponse(repo.save(u));
    }

    @Override
    public void deleteUser(UUID userId) {
        repo.deleteById(userId);
    }

    private UserResponse toResponse(User u) {
        return UserResponse.builder()
                .userId(u.getUserId())
                .username(u.getUsername())
                .email(u.getEmail())
                .profilePictureUrl(u.getProfilePictureUrl())
                .bio(u.getBio())
                .isCreator(u.getIsCreator())
                .createdAt(u.getCreatedAt())
                .lastLoginAt(u.getLastLoginAt())
                .build();
    }
}
