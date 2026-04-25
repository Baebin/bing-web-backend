package com.piebin.bingweb.features.auth.service;

import com.piebin.bingweb.features.auth.dto.request.SignUpRequest;

public interface AuthService {
    void signUp(SignUpRequest request);
}
