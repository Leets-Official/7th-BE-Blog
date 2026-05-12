package com.example.leets7th.domain.auth.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
