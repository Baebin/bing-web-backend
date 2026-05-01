package com.piebin.bingweb.features.post.service.impl;

import com.piebin.bingweb.features.post.dto.internal.PostDto;
import com.piebin.bingweb.features.post.dto.request.PostRequest;
import com.piebin.bingweb.features.post.service.CommunityService;
import com.piebin.bingweb.features.post.service.PostService;
import com.piebin.bingweb.global.security.SecurityAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final PostService postService;

    @Override
    @Transactional
    public void uploadPost(SecurityAccount securityAccount, PostRequest request) {
        postService.upload(
                PostDto.from(securityAccount.account().getIdx(), request)
        );
    }
}
