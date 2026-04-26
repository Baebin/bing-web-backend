package com.piebin.bingweb.features.auth.service.impl;

import com.piebin.bingweb.features.auth.dto.request.SignUpRequest;
import com.piebin.bingweb.features.auth.exception.AuthException;
import com.piebin.bingweb.features.auth.service.AuthService;
import com.piebin.bingweb.global.domain.Account;
import com.piebin.bingweb.global.exception.CustomException;
import com.piebin.bingweb.global.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void signUp(SignUpRequest request) {
        if (accountRepository.existsById(request.getId()))
            throw new CustomException(AuthException.DUPLICATE_ID);
        if (accountRepository.existsByEmail(request.getEmail()))
            throw new CustomException(AuthException.DUPLICATE_EMAIL);
        Account account = Account.builder()
                .id(request.getId())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        accountRepository.save(account);
    }
}
