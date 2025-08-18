package com.phuocnt.tiktok.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.phuocnt.tiktok.exception.ErrorCode;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    int code;        // HTTP status (vd: 200, 401, 404)
    String message;  // thông điệp
    T result;        // dữ liệu (có thể null)

    // Helpers
    public static <T> ApiResponse<T> of(ErrorCode ec, String message, T data) {
        return ApiResponse.<T>builder()
                .code(ec.getHttpStatus().value())
                .message(message != null ? message : ec.getDefaultMessage())
                .result(data)
                .build();
    }
    public static <T> ApiResponse<T> of(ErrorCode ec, T data) {
        return of(ec, null, data);
    }
    public static ApiResponse<Void> of(ErrorCode ec) {
        return of(ec, null, null);
    }
}
