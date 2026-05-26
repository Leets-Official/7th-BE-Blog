package com.example.demo.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "토큰 재발급 응답")
public record TokenRefreshResponse(
        @Schema(description = "새 액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9...")
        String accessToken,
        @Schema(description = "새 리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9...")
        String refreshToken,
        @Schema(description = "토큰 타입", example = "Bearer")
        String tokenType
) {
}
