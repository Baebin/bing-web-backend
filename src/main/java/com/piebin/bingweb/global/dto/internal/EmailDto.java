package com.piebin.bingweb.global.dto.internal;

import lombok.Builder;

@Builder
public record EmailDto(String toAddress, String title, String message) {
}
