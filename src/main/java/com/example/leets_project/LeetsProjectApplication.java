package com.example.leets_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@ConfigurationPropertiesScan("com.example.leets_project.common.security.jwt, com.example.leets_project.domain.auth.oauth.kakao" )
@SpringBootApplication
public class LeetsProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeetsProjectApplication.class, args);
	}

}
