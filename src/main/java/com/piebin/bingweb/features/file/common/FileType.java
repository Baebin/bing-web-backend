package com.piebin.bingweb.features.file.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileType {
    IMAGE("이미지", "images"),
    VIDEO("동영상", "videos"),
    DOCUMENT("문서", "documents"),
    ETC("기타", "etc");

    private final String description;
    private final String directory;
}
