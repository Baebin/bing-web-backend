package com.piebin.bingweb.features.account.dto.response;

import com.piebin.bingweb.global.annotation.BingDateTimeFormat;
import com.piebin.bingweb.global.domain.Account;
import lombok.Builder;
import org.springframework.util.StringUtils;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AccountResponse(
        String id,
        String nickname,
        String email,
        String bio,
        @BingDateTimeFormat
        LocalDateTime regDate
) {
    public static AccountResponse from(Account account) {
        String bio = StringUtils.hasText(account.getBio())
                ? account.getBio()
                : account.getIdx() + "번째 빙구단원";
        return AccountResponse.builder()
                .id(account.getId())
                .nickname(account.getNickname())
                .email(account.getEmail())
                .bio(bio)
                .regDate(account.getRegDate())
                .build();
    }
}
