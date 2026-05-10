package com.piebin.bingweb.features.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentRequest {
    private Long parentIdx;

    @NotBlank(message = "콘텐츠는 필수 입력값입니다.")
    @Size(min = 1, max = 50, message = "콘텐츠는 1자 이상 50자 이하로 입력해주세요.")
    private String content;
}
