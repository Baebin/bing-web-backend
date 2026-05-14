package com.piebin.bingweb.features.post.service;

import com.piebin.bingweb.features.post.dto.internal.CommentDto;
import com.piebin.bingweb.features.post.dto.response.CommentResponse;
import com.piebin.bingweb.features.post.dto.response.CommentWithPagingResponse;

public interface CommentService {
    void create(CommentDto dto);

    CommentResponse get(Long commentIdx, Long postIdx);
    CommentWithPagingResponse getList(Long postIdx, Long lastParentIdx);
    CommentWithPagingResponse getListUntil(Long postIdx, Long lastParentIdx);
}
