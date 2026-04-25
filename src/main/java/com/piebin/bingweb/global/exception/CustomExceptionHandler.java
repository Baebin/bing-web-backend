package com.piebin.bingweb.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ExceptionResponseDto> handleCustomException(CustomException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ExceptionResponseDto.from(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        String message = result.getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponseDto.of(HttpStatus.BAD_REQUEST, message));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionResponseDto> handleException(Exception e) {
        log.error("Unhandled Exception 발생: {}", e.getMessage());

        GlobalException exception = GlobalException.SERVER_ERROR;
        return ResponseEntity
                .status(exception.getStatus())
                .body(ExceptionResponseDto.from(exception));
    }
}
