package com.myoidc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthServerApplication {

	@Bean
	public CommandLineRunner checkJwtFilter(ApplicationContext ctx) {
		return args -> {
			System.out.println("JwtAuthenticationFilter in context: " + ctx.containsBeanDefinition("jwtAuthenticationFilter"));
		};
	}

	public static void main(String[] args) {

		SpringApplication.run(AuthServerApplication.class, args);
	}

}
