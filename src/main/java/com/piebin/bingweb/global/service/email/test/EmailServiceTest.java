package com.piebin.bingweb.global.service.email.test;

import com.piebin.bingweb.global.dto.internal.EmailDto;
import com.piebin.bingweb.global.service.email.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Test
    void send() {
        emailService.send(
                EmailDto.builder()
                        .toAddress("baebine@naver.com")
                        .title("Test Mail")
                        .message("BingGu is Babo")
                        .build()
        );
    }
}
