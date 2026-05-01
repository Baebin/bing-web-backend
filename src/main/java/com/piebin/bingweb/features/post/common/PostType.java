package com.piebin.bingweb.features.post.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostType {
    NOTICE("공지사항"),
    PORTFOLIO("포트폴리오"),
    BLOG("블로그"),
    COMMUNITY("커뮤니티");

    private final String description;
}
