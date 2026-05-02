package com.piebin.bingweb.features.post.service.impl;

import com.piebin.bingweb.features.account.exception.AccountException;
import com.piebin.bingweb.features.post.common.PostType;
import com.piebin.bingweb.features.post.domain.Post;
import com.piebin.bingweb.features.post.dto.internal.PostDto;
import com.piebin.bingweb.features.post.dto.response.PostListResponse;
import com.piebin.bingweb.features.post.dto.response.PostResponse;
import com.piebin.bingweb.features.post.exception.PostException;
import com.piebin.bingweb.features.post.repository.PostRepository;
import com.piebin.bingweb.features.post.service.PostService;
import com.piebin.bingweb.global.domain.Account;
import com.piebin.bingweb.global.exception.CustomException;
import com.piebin.bingweb.global.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void upload(PostDto dto) {
        Account author = accountRepository.findByIdx(dto.authorIdx())
                .orElseThrow(() -> new CustomException(AccountException.USER_NOT_FOUND));
        ;
        postRepository.save(
                Post.builder()
                        .author(author)
                        .title(dto.title())
                        .content(dto.content())
                        .type(dto.type())
                        .build()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse get(Long idx) {
        Post post = postRepository.findByIdx(idx)
                .orElseThrow(() -> new CustomException(PostException.POST_NOT_FOUND));
        return PostResponse.from(post);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostListResponse> getList(PostType type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("idx").descending());
        Page<Post> postPage = postRepository.findAllByType(type, pageable);
        return postPage.getContent().stream()
                .map(PostListResponse::from)
                .toList();
    }
}
