package com.phuocnt.tiktok.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    @Builder.Default int code = 200;
    @Builder.Default String message = "OK";
    T result;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder().code(200).message("OK").result(data).build();
    }
    public static <T> ApiResponse<T> created(T data) {
        return ApiResponse.<T>builder().code(201).message("Created").result(data).build();
    }
    public static ApiResponse<Void> noContent() {
        return ApiResponse.<Void>builder().code(204).message("No Content").build();
    }
    public static ApiResponse<Void> error(int code, String message) {
        return ApiResponse.<Void>builder().code(code).message(message).build();
    }
}
