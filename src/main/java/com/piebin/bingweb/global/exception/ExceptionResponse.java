package com.piebin.bingweb.global.exception;

import com.piebin.bingweb.global.annotation.BingDateTimeFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ExceptionResponse {
    private final String code;
    private final int status;
    private final String message;
    @BingDateTimeFormat
    private final LocalDateTime timestamp;

    private ExceptionResponse(String code, int status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    private ExceptionResponse(ErrorCode errorCode) {
        this.code = errorCode.getStatus().name();
        this.status = errorCode.getStatus().value();
        this.message = errorCode.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    private ExceptionResponse(CustomException exception) {
        this.code = exception.getErrorCode().getStatus().name();
        this.status = exception.getErrorCode().getStatus().value();
        this.message = exception.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public static ExceptionResponse of(HttpStatus status, String message) {
        return new ExceptionResponse(status.name(), status.value(), message);
    }

    public static ExceptionResponse from(ErrorCode e) {
        return new ExceptionResponse(e);
    }

    public static ExceptionResponse from(CustomException e) {
        return new ExceptionResponse(e);
    }
}
