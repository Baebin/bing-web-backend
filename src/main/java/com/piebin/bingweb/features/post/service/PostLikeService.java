package com.piebin.bingweb.features.post.service;

public interface PostLikeService {
    boolean toggle(Long postIdx, Long accountIdx);
}
