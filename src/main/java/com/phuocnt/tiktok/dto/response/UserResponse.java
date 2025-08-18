package com.phuocnt.tiktok.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    UUID userId;
    String username;
    String email;
    String profilePictureUrl;
    String bio;
    Boolean isCreator;
    Instant createdAt;
    Instant lastLoginAt;
}
