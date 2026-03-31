package com.example.leets_7th;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
public class Leets7thApplication {

	public static void main(String[] args) {
		SpringApplication.run(Leets7thApplication.class, args);
	}

}
