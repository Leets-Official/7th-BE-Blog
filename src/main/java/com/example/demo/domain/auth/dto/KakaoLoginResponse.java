package com.example.demo.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "카카오 로그인 응답")
public record KakaoLoginResponse(
        @Schema(description = "서비스 액세스 토큰", example = "eyJhbGciOiJIUzUxMiJ9...")
        String accessToken,
        @Schema(description = "서비스 리프레시 토큰", example = "eyJhbGciOiJIUzUxMiJ9...")
        String refreshToken,
        @Schema(description = "토큰 타입", example = "Bearer")
        String tokenType,
        @Schema(description = "이메일", example = "user@example.com")
        String email,
        @Schema(description = "닉네임", example = "tester")
        String nickname,
        @Schema(description = "최초 회원가입 여부", example = "true")
        boolean newlyRegistered
) {
}
