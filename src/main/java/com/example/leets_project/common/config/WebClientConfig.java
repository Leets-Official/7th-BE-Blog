package com.example.leets_project.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // OAuth 전용(인가 코드 교환, 토큰 갱신..)
    @Bean
    public WebClient kakaoAuthWebClient() {
        return WebClient.builder()
                .baseUrl("https://kauth.kakao.com")
                .build();
    }

    // 오픈 API 전용(유저 프로필 조회, 목록 조회..)
    @Bean
    public WebClient kakaoApiWebClient() {
        return WebClient.builder()
                .baseUrl("https://kapi.kakao.com")
                .build();
    }
}