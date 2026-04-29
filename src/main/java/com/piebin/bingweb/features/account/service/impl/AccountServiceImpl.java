package com.piebin.bingweb.features.account.service.impl;

import com.piebin.bingweb.features.account.dto.request.NicknameUpdateRequest;
import com.piebin.bingweb.features.account.dto.response.AccountResponse;
import com.piebin.bingweb.features.account.exception.AccountException;
import com.piebin.bingweb.features.auth.exception.AuthException;
import com.piebin.bingweb.features.account.service.AccountService;
import com.piebin.bingweb.global.domain.Account;
import com.piebin.bingweb.global.exception.CustomException;
import com.piebin.bingweb.global.repository.AccountRepository;
import com.piebin.bingweb.global.security.SecurityAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getMyProfile(SecurityAccount securityAccount) {
        if (securityAccount == null)
            throw new CustomException(AuthException.UNAUTHORIZED);
        return AccountResponse.from(securityAccount.account());
    }

    @Override
    @Transactional
    public void updateNickname(SecurityAccount securityAccount, NicknameUpdateRequest request) {
        if (securityAccount == null)
            throw new CustomException(AuthException.UNAUTHORIZED);
        Account account = accountRepository.findByIdx(securityAccount.account().getIdx())
                        .orElseThrow(() -> new CustomException(AccountException.USER_NOT_FOUND));
        if (!account.getNickname().equalsIgnoreCase(request.getNickname()))
            if (accountRepository.existsByNickname(request.getNickname()))
                throw new CustomException(AccountException.DUPLICATE_NICKNAME);
        account.setNickname(request.getNickname());
    }
}
