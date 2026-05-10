package com.piebin.bingweb.features.post.dto.internal;

import com.piebin.bingweb.features.post.dto.request.CommentRequest;
import lombok.Builder;

@Builder
public record CommentDto(
        Long postIdx,
        Long parentIdx,
        Long authorIdx,
        String content
) {
    public static CommentDto from(Long authorIdx, Long postIdx, CommentRequest request) {
        return CommentDto.builder()
                .postIdx(postIdx)
                .parentIdx(request.getParentIdx())
                .authorIdx(authorIdx)
                .content(request.getContent())
                .build();
    }
}
