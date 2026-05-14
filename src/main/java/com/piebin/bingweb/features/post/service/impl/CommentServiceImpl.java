package com.piebin.bingweb.features.post.service.impl;

import com.piebin.bingweb.features.account.exception.AccountException;
import com.piebin.bingweb.features.post.domain.Comment;
import com.piebin.bingweb.features.post.domain.Post;
import com.piebin.bingweb.features.post.dto.internal.CommentDto;
import com.piebin.bingweb.features.post.dto.response.CommentResponse;
import com.piebin.bingweb.features.post.dto.response.CommentWithPagingResponse;
import com.piebin.bingweb.features.post.exception.CommentException;
import com.piebin.bingweb.features.post.exception.PostException;
import com.piebin.bingweb.features.post.repository.CommentRepository;
import com.piebin.bingweb.features.post.repository.PostRepository;
import com.piebin.bingweb.features.post.service.CommentService;
import com.piebin.bingweb.global.domain.Account;
import com.piebin.bingweb.global.exception.CustomException;
import com.piebin.bingweb.global.repository.AccountRepository;
import com.piebin.bingweb.global.utils.BingUrlProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final AccountRepository accountRepository;

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final BingUrlProvider bingUrlProvider;

    @Override
    @Transactional
    public void create(CommentDto dto) {
        Account author = accountRepository.findByIdx(dto.authorIdx())
                .orElseThrow(() -> new CustomException(AccountException.USER_NOT_FOUND));
        Post post = postRepository.findByIdx(dto.postIdx())
                .orElseThrow(() -> new CustomException(PostException.POST_NOT_FOUND));
        Comment parent = null;
        if (dto.parentIdx() != null) {
            Comment targetParent = commentRepository.findByIdx(dto.parentIdx())
                    .orElseThrow(() -> new CustomException(CommentException.COMMENT_NOT_FOUND));
            parent = (targetParent.getParent() != null) ? targetParent.getParent() : targetParent;
        }
        Comment comment = Comment.from(dto, post, author, parent);
        commentRepository.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponse get(Long postIdx, Long commentIdx) {
        Comment comment = commentRepository.findByIdxAndPostIdx(commentIdx, postIdx)
                .orElseThrow(() -> new CustomException(CommentException.COMMENT_NOT_FOUND));
        return CommentResponse.from(comment, bingUrlProvider);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentWithPagingResponse getList(Long postIdx, Long lastParentIdx) {
        Pageable pageable = PageRequest.of(0, 20);
        List<Comment> parents = commentRepository.findParents(postIdx, lastParentIdx, pageable);

        int totalCount = 0; boolean isLast = true;
        List<CommentResponse> result = new ArrayList<>();
        for (int i = 0; i < parents.size(); i++) {
            Comment parent = parents.get(i);
            List<Comment> children = parent.getChildren();
            int groupSize = 1 + children.size();

            result.add(CommentResponse.from(parent, bingUrlProvider));
            totalCount += groupSize;

            if (totalCount >= 10) {
                if (i < parents.size() - 1) isLast = false;
                break;
            }
        }
        return CommentWithPagingResponse.of(result, isLast);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentWithPagingResponse getListUntil(Long postIdx, Long lastParentIdx) {
        List<Comment> parents = commentRepository.findParentsUntil(postIdx, lastParentIdx);
        List<CommentResponse> result = parents.stream()
                .map(p -> CommentResponse.from(p, bingUrlProvider))
                .toList();
        boolean isLast = (result.size() == commentRepository.countByPostIdxAndParentIsNull(postIdx));
        return CommentWithPagingResponse.of(result, isLast);
    }
}
