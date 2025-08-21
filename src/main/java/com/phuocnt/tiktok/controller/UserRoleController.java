package com.phuocnt.tiktok.controller;

import com.phuocnt.tiktok.exception.ErrorCode;
import com.phuocnt.tiktok.dto.request.RoleRequest;
import com.phuocnt.tiktok.dto.response.ApiResponse;
import com.phuocnt.tiktok.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users/{userId}/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRoleController {

    UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addRole(@PathVariable UUID userId,
                                                     @RequestBody @Valid RoleRequest req) {
        userService.addRole(userId, req.getRoleName());
        return ResponseEntity.status(ErrorCode.OK.getHttpStatus())
                .body(ApiResponse.of(ErrorCode.OK));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> removeRole(@PathVariable UUID userId,
                                                        @RequestBody @Valid RoleRequest req) {
        userService.removeRole(userId, req.getRoleName());
        return ResponseEntity.status(ErrorCode.NO_CONTENT.getHttpStatus())
                .body(ApiResponse.of(ErrorCode.NO_CONTENT));
    }
}
