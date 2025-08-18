package com.phuocnt.tiktok.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    OK(               HttpStatus.OK,                 "OK"),
    CREATED(          HttpStatus.CREATED,            "Created"),
    NO_CONTENT(       HttpStatus.NO_CONTENT,         "No Content"),

    VALIDATION_FAILED(HttpStatus.BAD_REQUEST,        "Validation failed"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED,     "Invalid username or password"),
    FORBIDDEN(        HttpStatus.FORBIDDEN,          "Forbidden"),
    NOT_FOUND(        HttpStatus.NOT_FOUND,          "Resource not found"),
    CONFLICT(         HttpStatus.CONFLICT,           "Conflict"),
    INTERNAL_ERROR(   HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private final HttpStatus httpStatus;
    private final String defaultMessage;

    ErrorCode(HttpStatus httpStatus, String defaultMessage) {
        this.httpStatus = httpStatus;
        this.defaultMessage = defaultMessage;
    }
}
