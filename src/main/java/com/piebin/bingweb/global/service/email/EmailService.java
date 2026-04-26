package com.piebin.bingweb.global.service.email;

import com.piebin.bingweb.global.dto.internal.EmailDto;

public interface EmailService {
    void send(String toAddress, String title, String message);
    void send (EmailDto dto);
}
