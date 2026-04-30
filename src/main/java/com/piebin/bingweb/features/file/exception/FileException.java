package com.piebin.bingweb.features.file.exception;

import com.piebin.bingweb.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FileException implements ErrorCode {
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일 정보를 찾을 수 없습니다."),
    FILE_STORAGE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 시스템 저장 중 오류가 발생했습니다."),
    INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 형식입니다.");

    private final HttpStatus status;
    private final String message;
}
