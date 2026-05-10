package com.piebin.bingweb.features.post.exception;

import com.piebin.bingweb.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentException implements ErrorCode {
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),

    INVALID_PARENT_POST(HttpStatus.BAD_REQUEST, "부모 댓글과 게시글 정보가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
