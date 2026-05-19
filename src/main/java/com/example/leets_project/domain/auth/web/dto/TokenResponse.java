package com.example.leets_project.domain.auth.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "토큰 응답")
public record TokenResponse(
        @Schema(description = "Access Token (Bearer)", example = "eyJhbGci...")
        String accessToken
) {}