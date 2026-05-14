package com.piebin.bingweb.features.post.dto.response;

import com.piebin.bingweb.features.post.common.PostType;
import com.piebin.bingweb.features.post.domain.Post;
import com.piebin.bingweb.global.annotation.BingDateTimeFormat;
import com.piebin.bingweb.global.domain.Account;
import lombok.*;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PostResponse(
        Long idx,
        Long authorIdx,
        String authorNickname,
        String title,
        String content,
        PostType type,
        int viewCount,
        int likeCount,
        int commentCount,
        boolean isLiked,
        @BingDateTimeFormat
        LocalDateTime createdAt,
        @BingDateTimeFormat
        LocalDateTime updatedAt
) {
    public static PostResponse from(Post post, boolean isLiked) {
        Account author = post.getAuthor();
        return PostResponse.builder()
                .idx(post.getIdx())
                .authorIdx(author.getIdx())
                .authorNickname(author.getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .type(post.getType())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .isLiked(isLiked)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
