package com.piebin.bingweb.features.account.service;

import com.piebin.bingweb.features.account.dto.request.NicknameUpdateRequest;
import com.piebin.bingweb.features.account.dto.response.AccountResponse;
import com.piebin.bingweb.global.security.SecurityAccount;

public interface AccountService {
    AccountResponse getMyProfile(SecurityAccount securityAccount);

    void updateNickname(SecurityAccount securityAccount, NicknameUpdateRequest request);
}
