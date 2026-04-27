package com.piebin.bingweb.global.exception;

import com.piebin.bingweb.global.annotation.BingDateTimeFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ExceptionResponseDto {
    private final String code;
    private final int status;
    private final String message;
    @BingDateTimeFormat
    private final LocalDateTime timestamp;

    private ExceptionResponseDto(String code, int status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    private ExceptionResponseDto(ErrorCode errorCode) {
        this.code = errorCode.getStatus().name();
        this.status = errorCode.getStatus().value();
        this.message = errorCode.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    private ExceptionResponseDto(CustomException exception) {
        this.code = exception.getErrorCode().getStatus().name();
        this.status = exception.getErrorCode().getStatus().value();
        this.message = exception.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public static ExceptionResponseDto of(HttpStatus status, String message) {
        return new ExceptionResponseDto(status.name(), status.value(), message);
    }

    public static ExceptionResponseDto from(ErrorCode e) {
        return new ExceptionResponseDto(e);
    }

    public static ExceptionResponseDto from(CustomException e) {
        return new ExceptionResponseDto(e);
    }
}
