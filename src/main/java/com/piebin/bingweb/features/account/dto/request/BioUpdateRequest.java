package com.piebin.bingweb.features.account.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BioUpdateRequest {
    @NotBlank(message = "프로필 소개는 필수 입력값입니다.")
    @Size(min = 1, max = 150, message = "프로필 소개는 1자 이상 150자 이하로 입력해주세요.")
    private String bio;
}
