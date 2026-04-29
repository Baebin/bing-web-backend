package com.piebin.bingweb.features.account.controller;

import com.piebin.bingweb.features.account.dto.request.NicknameUpdateRequest;
import com.piebin.bingweb.features.account.dto.response.AccountResponse;
import com.piebin.bingweb.features.account.service.AccountService;
import com.piebin.bingweb.global.security.SecurityAccount;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/me/nickname")
    public ResponseEntity<Boolean> updateNickname(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid NicknameUpdateRequest request) {
        accountService.updateNickname(securityAccount, request);
        return ResponseEntity.ok(true);
    }
}
