package com.piebin.bingweb.features.auth.service.impl;

import com.piebin.bingweb.features.auth.dto.request.LoginRequest;
import com.piebin.bingweb.features.auth.dto.request.SignUpRequest;
import com.piebin.bingweb.features.auth.dto.response.AccountResponse;
import com.piebin.bingweb.features.auth.exception.AuthException;
import com.piebin.bingweb.features.auth.service.AuthService;
import com.piebin.bingweb.global.domain.Account;
import com.piebin.bingweb.global.dto.response.TokenResponse;
import com.piebin.bingweb.global.exception.CustomException;
import com.piebin.bingweb.global.repository.AccountRepository;
import com.piebin.bingweb.global.security.JwtProvider;
import com.piebin.bingweb.global.security.SecurityAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public void signUp(SignUpRequest request) {
        if (accountRepository.existsById(request.getId()))
            throw new CustomException(AuthException.DUPLICATE_ID);
        if (accountRepository.existsByNickname(request.getNickname()))
            throw new CustomException(AuthException.DUPLICATE_NICKNAME);
        if (accountRepository.existsByEmail(request.getEmail()))
            throw new CustomException(AuthException.DUPLICATE_EMAIL);
        Account account = Account.builder()
                .id(request.getId())
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest request) {
        Account account = accountRepository.findById(request.getId())
                .orElseThrow(() -> new CustomException(AuthException.USER_NOT_FOUND));
        if (!passwordEncoder.matches(request.getPassword(), account.getPassword()))
            throw new CustomException(AuthException.WRONG_PASSWORD);
        String accessToken = jwtProvider.createToken(account.getId());
        return TokenResponse.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .expiresIn(jwtProvider.getExpirationSeconds())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getMyProfile(SecurityAccount securityAccount) {
        if (securityAccount == null)
            throw new CustomException(AuthException.UNAUTHORIZED);
        return AccountResponse.from(securityAccount.account());
    }
}
