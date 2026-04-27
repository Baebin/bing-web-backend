package com.piebin.bingweb.features.auth.service;

import com.piebin.bingweb.features.auth.dto.request.LoginRequest;
import com.piebin.bingweb.features.auth.dto.request.SignUpRequest;
import com.piebin.bingweb.global.dto.response.TokenResponse;

public interface AuthService {
    void signUp(SignUpRequest request);
    TokenResponse login(LoginRequest request);
}
