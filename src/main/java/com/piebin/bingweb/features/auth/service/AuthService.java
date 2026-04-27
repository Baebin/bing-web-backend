package com.piebin.bingweb.features.auth.service;

import com.piebin.bingweb.features.auth.dto.request.LoginRequest;
import com.piebin.bingweb.features.auth.dto.request.SignUpRequest;
import com.piebin.bingweb.features.auth.dto.response.AccountResponse;
import com.piebin.bingweb.global.dto.response.TokenResponse;
import com.piebin.bingweb.global.security.SecurityAccount;

public interface AuthService {
    void signUp(SignUpRequest request);
    TokenResponse login(LoginRequest request);

    AccountResponse getMyProfile(SecurityAccount securityAccount);
}
