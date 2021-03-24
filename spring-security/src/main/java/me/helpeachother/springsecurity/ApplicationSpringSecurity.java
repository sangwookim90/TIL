package me.helpeachother.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ApplicationSpringSecurity {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationSpringSecurity.class, args);
	}
}
