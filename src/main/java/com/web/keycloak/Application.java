package com.web.keycloak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication (scanBasePackages = { "com.web.keycloak" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);			
	}

}

