package com.piebin.bingweb.features.post.service;

import com.piebin.bingweb.features.post.common.PostType;
import com.piebin.bingweb.features.post.dto.internal.PostDto;
import com.piebin.bingweb.features.post.dto.response.PostResponse;
import com.piebin.bingweb.features.post.dto.response.PostWithPagingResponse;

public interface PostService {
    void upload(PostDto dto);

    PostResponse get(Long idx);
    PostWithPagingResponse getList(PostType type, int page, int size);
}
