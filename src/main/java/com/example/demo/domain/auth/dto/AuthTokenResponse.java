package com.example.demo.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "인증 토큰 응답")
public record AuthTokenResponse(
        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9...")
        String accessToken,
        @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9...")
        String refreshToken,
        @Schema(description = "토큰 타입", example = "Bearer")
        String tokenType,
        @Schema(description = "사용자 ID", example = "1")
        Long userId,
        @Schema(description = "이메일", example = "user@example.com")
        String email,
        @Schema(description = "닉네임", example = "tester")
        String nickname
) {
}
