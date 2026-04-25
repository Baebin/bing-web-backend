package com.piebin.bingweb.global.security;

import com.piebin.bingweb.global.domain.Account;
import com.piebin.bingweb.global.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityAccountService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자 : " + id));
        return new SecurityAccount(account);
    }
}
