package com.piebin.bingweb.features.post.exception;

import com.piebin.bingweb.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostException implements ErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시물입니다.");

    private final HttpStatus status;
    private final String message;
}
