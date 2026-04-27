package com.piebin.bingweb.features.auth.controller;

import com.piebin.bingweb.features.auth.dto.request.LoginRequest;
import com.piebin.bingweb.features.auth.dto.request.SignUpRequest;
import com.piebin.bingweb.features.auth.dto.response.AccountResponse;
import com.piebin.bingweb.features.auth.service.AuthService;
import com.piebin.bingweb.global.dto.response.TokenResponse;
import com.piebin.bingweb.global.security.SecurityAccount;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/profile/me")
    public ResponseEntity<AccountResponse> getMyProfile(
            @AuthenticationPrincipal SecurityAccount securityAccount) {
        return ResponseEntity.ok(authService.getMyProfile(securityAccount));
    }

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signUp(
            @RequestBody @Valid SignUpRequest request) {
        authService.signUp(request);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
