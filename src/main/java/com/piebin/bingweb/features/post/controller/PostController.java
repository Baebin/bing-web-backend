package com.piebin.bingweb.features.post.controller;

import com.piebin.bingweb.features.post.common.PostType;
import com.piebin.bingweb.features.post.dto.request.PostRequest;
import com.piebin.bingweb.features.post.dto.response.PostResponse;
import com.piebin.bingweb.features.post.dto.response.PostWithPagingResponse;
import com.piebin.bingweb.features.post.service.CommunityService;
import com.piebin.bingweb.features.post.service.PostService;
import com.piebin.bingweb.global.security.SecurityAccount;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommunityService communityService;

    @PostMapping
    public ResponseEntity<Boolean> upload(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid PostRequest request) {
        switch (request.getType()) {
            case COMMUNITY -> communityService.uploadPost(securityAccount, request);
        }
        return ResponseEntity.ok(true);
    }

    @GetMapping("/{idx}")
    public ResponseEntity<PostResponse> get(
            @PathVariable Long idx) {
        return ResponseEntity.ok(
                postService.get(idx)
        );
    }

    @GetMapping
    public ResponseEntity<PostWithPagingResponse> getList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam PostType type) {
        return ResponseEntity.ok(
                postService.getList(type, page, size)
        );
    }
}
