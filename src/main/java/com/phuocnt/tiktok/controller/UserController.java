package com.phuocnt.tiktok.controller;

import com.phuocnt.tiktok.dto.request.UserRequest;
import com.phuocnt.tiktok.dto.request.UserUpdateRequest;
import com.phuocnt.tiktok.dto.response.ApiResponse;
import com.phuocnt.tiktok.dto.response.UserResponse;
import com.phuocnt.tiktok.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody @Valid UserRequest request){
        var data = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(data));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsers(){
        var data = userService.getUsers();
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable("userId") UUID userId){
        var data = userService.getUser(userId);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable UUID userId,
                                                                @RequestBody @Valid UserUpdateRequest request){
        var data = userService.updateUser(userId, request);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID userId){
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.noContent());
    }
}
