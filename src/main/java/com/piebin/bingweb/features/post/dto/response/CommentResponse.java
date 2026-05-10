package com.piebin.bingweb.features.post.dto.response;

import com.piebin.bingweb.features.post.domain.Comment;
import com.piebin.bingweb.global.annotation.BingDateTimeFormat;
import com.piebin.bingweb.global.domain.Account;
import com.piebin.bingweb.global.utils.BingUrlProvider;
import lombok.Builder;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CommentResponse(
        Long idx,
        Long parentIdx,
        Long authorIdx,
        String authorNickname,
        String authorAvatarUrl,
        String content,
        @BingDateTimeFormat
        LocalDateTime createdAt,
        @BingDateTimeFormat
        LocalDateTime updatedAt,

        List<CommentResponse> children
) {
    public static CommentResponse from(Comment comment, BingUrlProvider bingUrlProvider) {
        Account author = comment.getAuthor();
        return CommentResponse.builder()
                .idx(comment.getIdx())
                .parentIdx(comment.getParent() != null ? comment.getParent().getIdx() : null)
                .authorIdx(author.getIdx())
                .authorNickname(author.getNickname())
                .authorAvatarUrl(bingUrlProvider.getAvatarUrl(author.getIdx()))
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())

                .children(comment.getChildren().stream()
                        .map(c -> CommentResponse.from(c, bingUrlProvider))
                        .toList())
                .build();
    }
}
