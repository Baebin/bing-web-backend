package com.piebin.bingweb.features.post.controller;

import com.piebin.bingweb.features.post.service.PostLikeService;
import com.piebin.bingweb.global.security.SecurityAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postIdx}/like")
@RequiredArgsConstructor
public class PostLikeController {
    private final PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<Boolean> toggle(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @PathVariable Long postIdx) {
        return ResponseEntity.ok(
                postLikeService.toggle(postIdx, securityAccount.account().getIdx())
        );
    }
}
