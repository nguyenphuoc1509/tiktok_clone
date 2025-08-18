package com.phuocnt.tiktok.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    @Size(min = 3, max = 50)
    String username;

    @Email
    String email;

    @Size(min = 6, message = "password must be at least 6 characters")
    String password;

    @Size(max = 2048)
    String profilePictureUrl;

    @Size(max = 500)
    String bio;

    Boolean isCreator;
}
