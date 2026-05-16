package com.example.week2.auth.dto;

public class AuthResponse {

    public record SignupResponse(
            Long id,
            String nickname
    ) {}

    public record TokenResult(
            String accessToken,
            String refreshToken
    ) {}

    public record AccessToken(
            String accessToken
    ) {}
}