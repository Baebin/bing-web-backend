package com.piebin.bingweb.features.post.dto.response;

import com.piebin.bingweb.features.post.domain.Post;
import lombok.Builder;
import org.springframework.data.domain.Page;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PostWithPagingResponse(
        List<PostListResponse> posts,

        int totalPages,
        long totalElements,
        int currentPage,
        boolean isLast
) {
    public static PostWithPagingResponse from(Page<Post> page) {
        return PostWithPagingResponse.builder()
                .posts(page.getContent().stream()
                        .map(PostListResponse::from)
                        .toList())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .currentPage(page.getNumber())
                .isLast(page.isLast())
                .build();
    }
}
