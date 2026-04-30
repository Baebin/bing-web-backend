package com.piebin.bingweb.features.account.controller;

import com.piebin.bingweb.features.account.dto.request.BioUpdateRequest;
import com.piebin.bingweb.features.account.dto.request.NicknameUpdateRequest;
import com.piebin.bingweb.features.account.dto.response.AccountResponse;
import com.piebin.bingweb.features.account.service.AccountService;
import com.piebin.bingweb.global.security.SecurityAccount;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/me")
    public ResponseEntity<AccountResponse> getMyProfile(
            @AuthenticationPrincipal SecurityAccount securityAccount) {
        return ResponseEntity.ok(accountService.getMyProfile(securityAccount));
    }

    @GetMapping("/me/avatar")
    public ResponseEntity<Resource> getMyAvatar(
            @AuthenticationPrincipal SecurityAccount securityAccount) {
        return accountService.getMyAvatar(securityAccount);
    }

    @GetMapping("/{idx}/avatar")
    public ResponseEntity<Resource> getAvatar(@PathVariable Long idx) {
        return accountService.getAvatar(idx);
    }

    @PatchMapping(value = "/me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> updateAvatar(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestPart("file") MultipartFile file) {
        accountService.updateAvatar(securityAccount, file);
        return ResponseEntity.ok(true);
    }

    @PatchMapping("/me/nickname")
    public ResponseEntity<Boolean> updateNickname(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid NicknameUpdateRequest request) {
        accountService.updateNickname(securityAccount, request);
        return ResponseEntity.ok(true);
    }

    @PatchMapping("/me/bio")
    public ResponseEntity<Boolean> updateBio(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid BioUpdateRequest request) {
        accountService.updateBio(securityAccount, request);
        return ResponseEntity.ok(true);
    }
}
