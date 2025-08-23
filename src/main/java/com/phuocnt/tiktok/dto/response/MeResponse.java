package com.phuocnt.tiktok.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MeResponse {
    UUID userId;
    String username;
    String email;   
    List<String> roles;
}
