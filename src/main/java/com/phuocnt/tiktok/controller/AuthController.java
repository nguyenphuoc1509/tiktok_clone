package com.phuocnt.tiktok.controller;

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
        var payload = AuthResponse.builder()
                .accessToken(token)
                .expiresIn(jwtService.getExpirationSeconds())
                .isAuthenticated(true)
                .build();

        return ResponseEntity.status(ErrorCode.OK.getHttpStatus())
                .body(ApiResponse.of(ErrorCode.OK, payload));
    }
}
