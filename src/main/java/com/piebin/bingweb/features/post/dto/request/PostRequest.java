package com.piebin.bingweb.features.post.dto.request;

import com.piebin.bingweb.features.post.common.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostRequest {
    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(min = 2, max = 20, message = "제목은 2자 이상 20자 이하로 입력해주세요.")
    private String title;

    @NotBlank(message = "콘텐츠는 필수 입력값입니다.")
    private String content;

    @NotNull(message = "게시글 타입은 필수 입력값입니다.")
    private PostType type;
}
