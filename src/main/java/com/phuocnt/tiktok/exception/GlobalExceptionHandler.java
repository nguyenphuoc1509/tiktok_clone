package com.phuocnt.tiktok.config;

import com.phuocnt.tiktok.exception.ErrorCode;
import com.phuocnt.tiktok.dto.response.ApiResponse;
import com.phuocnt.tiktok.exception.AppException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 – Validation: trả map field -> message
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> fieldErrors.put(err.getField(), err.getDefaultMessage()));
        var ec = ErrorCode.VALIDATION_FAILED;
        return ResponseEntity.status(ec.getHttpStatus())
                .body(ApiResponse.of(ec, ec.getDefaultMessage(), fieldErrors));
    }

    // AppException: lấy ErrorCode bên trong
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Void>> handleApp(AppException ex) {
        var ec = ex.getErrorCode();
        return ResponseEntity.status(ec.getHttpStatus())
                .body(ApiResponse.of(ec, ex.getMessage(), null));
    }

    // 404 – dữ liệu không tồn tại (trường hợp bạn ném NoSuchElementException)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(NoSuchElementException ex) {
        var ec = ErrorCode.NOT_FOUND;
        return ResponseEntity.status(ec.getHttpStatus())
                .body(ApiResponse.of(ec, ex.getMessage(), null));
    }

    // 409 – conflict (nếu dùng IllegalArgumentException cho duplicate, v.v.)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleConflict(IllegalArgumentException ex) {
        var ec = ErrorCode.CONFLICT;
        return ResponseEntity.status(ec.getHttpStatus())
                .body(ApiResponse.of(ec, ex.getMessage(), null));
    }

    // 500 – fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleOther(Exception ex) {
        var ec = ErrorCode.INTERNAL_ERROR;
        return ResponseEntity.status(ec.getHttpStatus())
                .body(ApiResponse.of(ec));
    }
}
