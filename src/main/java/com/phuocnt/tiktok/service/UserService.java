package com.phuocnt.tiktok.service;

import com.phuocnt.tiktok.dto.request.UserRequest;
import com.phuocnt.tiktok.dto.request.UserUpdateRequest;
import com.phuocnt.tiktok.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse createUser(UserRequest req);
    List<UserResponse> getUsers();
    UserResponse getUser(UUID userId);
    UserResponse updateUser(UUID userId, UserUpdateRequest req);
    void deleteUser(UUID userId);
}
