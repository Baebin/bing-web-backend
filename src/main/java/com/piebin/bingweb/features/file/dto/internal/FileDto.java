package com.piebin.bingweb.features.file.dto.internal;

import lombok.Builder;

@Builder
public record FileDto(
        String path,
        String name
) {}
