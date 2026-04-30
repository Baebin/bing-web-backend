package com.piebin.bingweb.features.account.service;

import com.piebin.bingweb.features.account.dto.request.BioUpdateRequest;
import com.piebin.bingweb.features.account.dto.request.NicknameUpdateRequest;
import com.piebin.bingweb.features.account.dto.response.AccountResponse;
import com.piebin.bingweb.global.security.SecurityAccount;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {
    AccountResponse getMyProfile(SecurityAccount securityAccount);
    ResponseEntity<Resource> getMyAvatar(SecurityAccount securityAccount);

    void updateAvatar(SecurityAccount securityAccount, MultipartFile file);

    void updateNickname(SecurityAccount securityAccount, NicknameUpdateRequest request);
    void updateBio(SecurityAccount securityAccount, BioUpdateRequest request);
}
