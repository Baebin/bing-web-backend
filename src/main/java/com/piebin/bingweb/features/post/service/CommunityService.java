package com.piebin.bingweb.features.post.service;

import com.piebin.bingweb.features.post.dto.request.PostRequest;
import com.piebin.bingweb.global.security.SecurityAccount;

public interface CommunityService {
    void uploadPost(SecurityAccount securityAccount, PostRequest request);
}
