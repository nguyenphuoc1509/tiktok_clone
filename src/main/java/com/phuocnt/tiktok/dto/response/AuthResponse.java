package com.phuocnt.tiktok.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse {
    String accessToken;
    long expiresIn;       // seconds
    boolean isAuthenticated;  // true nếu login thành công
}
