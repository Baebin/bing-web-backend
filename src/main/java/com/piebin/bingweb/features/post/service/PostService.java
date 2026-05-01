package com.piebin.bingweb.features.post.service;

import com.piebin.bingweb.features.post.dto.internal.PostDto;
import com.piebin.bingweb.features.post.dto.response.PostResponse;

public interface PostService {
    void upload(PostDto dto);

    PostResponse get(Long idx);
}
