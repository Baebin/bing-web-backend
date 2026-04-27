package com.piebin.bingweb.features.auth.controller;

import com.piebin.bingweb.features.auth.dto.request.LoginRequest;
import com.piebin.bingweb.features.auth.dto.request.SignUpRequest;
import com.piebin.bingweb.features.auth.service.AuthService;
import com.piebin.bingweb.global.dto.response.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

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
