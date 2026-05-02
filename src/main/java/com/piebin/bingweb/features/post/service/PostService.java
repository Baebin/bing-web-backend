package com.piebin.bingweb.features.post.service;

import com.piebin.bingweb.features.post.common.PostType;
import com.piebin.bingweb.features.post.dto.internal.PostDto;
import com.piebin.bingweb.features.post.dto.response.PostListResponse;
import com.piebin.bingweb.features.post.dto.response.PostResponse;

import java.util.List;

public interface PostService {
    void upload(PostDto dto);

    PostResponse get(Long idx);
    List<PostListResponse> getList(PostType type, int page, int size);
}
