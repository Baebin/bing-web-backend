package com.piebin.bingweb.features.post.service.impl;

import com.piebin.bingweb.features.account.exception.AccountException;
import com.piebin.bingweb.features.post.domain.Post;
import com.piebin.bingweb.features.post.domain.PostLike;
import com.piebin.bingweb.features.post.exception.PostException;
import com.piebin.bingweb.features.post.repository.PostLikeRepository;
import com.piebin.bingweb.features.post.repository.PostRepository;
import com.piebin.bingweb.features.post.service.PostLikeService;
import com.piebin.bingweb.global.domain.Account;
import com.piebin.bingweb.global.exception.CustomException;
import com.piebin.bingweb.global.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public boolean toggle(Long postIdx, Long accountIdx) {
        Post post = postRepository.findById(postIdx)
                .orElseThrow(() -> new CustomException(PostException.POST_NOT_FOUND));
        return postLikeRepository.findByPostIdxAndAccountIdx(postIdx, accountIdx)
                .map(like -> {
                    postLikeRepository.delete(like);
                    return false;
                })
                .orElseGet(() -> {
                    PostLike like = PostLike.builder()
                            .post(post)
                            .account(accountRepository.getReferenceById(accountIdx))
                            .build();
                    postLikeRepository.save(like);
                    return true;
                });
    }
}
