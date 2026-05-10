package com.piebin.bingweb.global.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BingUrlProvider {
    @Value("${bingweb.api.url}")
    private String url;

    public String getAvatarUrl(Long accountIdx) {
        return String.format("%s/api/accounts/%d/avatar", url, accountIdx);
    }
}
