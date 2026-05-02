package com.piebin.bingweb.features.post.dto.internal;

import com.piebin.bingweb.features.post.common.PostType;
import com.piebin.bingweb.features.post.dto.request.PostRequest;
import lombok.Builder;

@Builder
public record PostDto(
        Long authorIdx,
        String title,
        String content,
        PostType type
) {
    public static PostDto from(Long authorIdx, PostRequest request) {
        return PostDto.builder()
                .authorIdx(authorIdx)
                .title(request.getTitle())
                .content(request.getContent())
                .type(request.getType())
                .build();
    }
}
