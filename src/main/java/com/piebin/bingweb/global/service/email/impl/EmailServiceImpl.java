package com.piebin.bingweb.global.service.email.impl;

import com.piebin.bingweb.global.dto.internal.EmailDto;
import com.piebin.bingweb.global.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Override
    public void send(String toAddress, String title, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(toAddress);
        mail.setSubject(title);
        mail.setText(message);
        javaMailSender.send(mail);
    }

    @Override
    public void send(EmailDto dto) {
        send(dto.toAddress(), dto.title(), dto.message());
    }
}
