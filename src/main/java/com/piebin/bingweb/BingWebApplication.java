package com.piebin.bingweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BingWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(BingWebApplication.class, args);
	}

}
