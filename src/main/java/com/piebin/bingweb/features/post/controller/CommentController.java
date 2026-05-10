package com.piebin.bingweb.features.post.controller;

import com.piebin.bingweb.features.post.dto.internal.CommentDto;
import com.piebin.bingweb.features.post.dto.request.CommentRequest;
import com.piebin.bingweb.features.post.dto.response.CommentResponse;
import com.piebin.bingweb.features.post.dto.response.CommentWithPagingResponse;
import com.piebin.bingweb.features.post.service.CommentService;
import com.piebin.bingweb.global.security.SecurityAccount;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postIdx}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Boolean> create(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @PathVariable Long postIdx,
            @RequestBody @Valid CommentRequest request) {
        commentService.create(
                CommentDto.from(securityAccount.account().getIdx(), postIdx, request)
        );
        return ResponseEntity.ok(true);
    }

    @GetMapping("/{commentIdx}")
    public ResponseEntity<CommentResponse> get(
            @PathVariable Long postIdx,
            @PathVariable Long commentIdx) {
        return ResponseEntity.ok(
                commentService.get(commentIdx, postIdx)
        );
    }

    @GetMapping
    public ResponseEntity<CommentWithPagingResponse> getList(
            @PathVariable Long postIdx,
            @RequestParam(name = "last-parent-idx", required = false) Long lastParentIdx) {
        return ResponseEntity.ok(
                commentService.getList(postIdx, lastParentIdx)
        );
    }
}
