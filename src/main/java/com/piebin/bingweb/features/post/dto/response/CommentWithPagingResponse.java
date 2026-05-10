package com.piebin.bingweb.features.post.dto.response;

import lombok.Builder;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CommentWithPagingResponse(
        List<CommentResponse> comments,

        Long lastParentIdx,
        boolean isLast
) {
    public static CommentWithPagingResponse of(List<CommentResponse> comments, boolean isLast) {
        Long lastIdx = comments.isEmpty() ? null : comments.getLast().idx();

        return CommentWithPagingResponse.builder()
                .comments(comments)
                .lastParentIdx(lastIdx)
                .isLast(isLast)
                .build();
    }
}
