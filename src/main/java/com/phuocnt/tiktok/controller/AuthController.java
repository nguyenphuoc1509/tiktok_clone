package com.phuocnt.tiktok.controller;

import com.phuocnt.tiktok.dto.response.MeResponse;
import com.phuocnt.tiktok.entity.Role;
import com.phuocnt.tiktok.exception.ErrorCode;
import com.phuocnt.tiktok.dto.request.AuthRequest;
import com.phuocnt.tiktok.dto.response.ApiResponse;
import com.phuocnt.tiktok.dto.response.AuthResponse;
import com.phuocnt.tiktok.exception.AppException;
import com.phuocnt.tiktok.repository.UserRepository;
import com.phuocnt.tiktok.security.JwtService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody @Valid AuthRequest req) {
        var user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        var token = jwtService.generateToken(user.getUsername());
        var roles = user.getRoles().stream().map(Role::getName).toList();

        var payload = AuthResponse.builder()
                .accessToken(token)
                .expiresIn(jwtService.getExpirationSeconds())
                .isAuthenticated(true)
                .userId(user.getUserId())
                .roles(roles)
                .build();

        return ResponseEntity.status(ErrorCode.OK.getHttpStatus())
                .body(ApiResponse.of(ErrorCode.OK, payload));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MeResponse>> me(java.security.Principal principal) {
        // principal.getName() chính là username đã set từ JwtAuthFilter
        var user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "User not found"));

        var roles = user.getRoles().stream().map(r -> r.getName()).toList();

        var payload = MeResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roles)
                .build();

        return ResponseEntity.status(ErrorCode.OK.getHttpStatus())
                .body(ApiResponse.of(ErrorCode.OK, payload));
    }


}
