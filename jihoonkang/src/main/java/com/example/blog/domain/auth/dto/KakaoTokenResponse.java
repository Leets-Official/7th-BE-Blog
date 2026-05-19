package com.example.blog.domain.auth.dto;

public record KakaoTokenResponse(
    String accessToken,
    String refreshToken,
    String tokenType,
    Long expiresIn
) {}
