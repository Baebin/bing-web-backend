package com.piebin.bingweb.features.account.service.impl;

import com.piebin.bingweb.features.account.dto.request.BioUpdateRequest;
import com.piebin.bingweb.features.account.dto.request.NicknameUpdateRequest;
import com.piebin.bingweb.features.account.dto.response.AccountResponse;
import com.piebin.bingweb.features.account.exception.AccountException;
import com.piebin.bingweb.features.account.service.AccountService;
import com.piebin.bingweb.global.domain.Account;
import com.piebin.bingweb.features.file.dto.internal.FileDto;
import com.piebin.bingweb.global.exception.CustomException;
import com.piebin.bingweb.global.repository.AccountRepository;
import com.piebin.bingweb.global.security.SecurityAccount;
import com.piebin.bingweb.features.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    private final FileService fileService;

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getMyProfile(SecurityAccount securityAccount) {
        return AccountResponse.from(securityAccount.account());
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Resource> getMyAvatar(SecurityAccount securityAccount) {
        return fileService.downloadResponse(buildAvatarDto(securityAccount.account()));
    }

    @Override
    @Transactional
    public void updateAvatar(SecurityAccount securityAccount, MultipartFile file) {
        fileService.upload(file, buildAvatarDto(securityAccount.account()));
    }

    @Override
    @Transactional
    public void updateNickname(SecurityAccount securityAccount, NicknameUpdateRequest request) {
        Account account = accountRepository.findByIdx(securityAccount.account().getIdx())
                        .orElseThrow(() -> new CustomException(AccountException.USER_NOT_FOUND));
        if (!account.getNickname().equalsIgnoreCase(request.getNickname()))
            if (accountRepository.existsByNickname(request.getNickname()))
                throw new CustomException(AccountException.DUPLICATE_NICKNAME);
        account.setNickname(request.getNickname());
    }

    @Override
    @Transactional
    public void updateBio(SecurityAccount securityAccount, BioUpdateRequest request) {
        Account account = accountRepository.findByIdx(securityAccount.account().getIdx())
                .orElseThrow(() -> new CustomException(AccountException.USER_NOT_FOUND));
        account.setBio(request.getBio());
    }

    private FileDto buildAvatarDto(Account account) {
        return FileDto.builder()
                .path("profiles/" + account.getIdx())
                .name("avatar")
                .build();
    }
}
