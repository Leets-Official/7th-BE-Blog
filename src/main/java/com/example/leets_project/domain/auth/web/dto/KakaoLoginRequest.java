package com.example.leets_project.domain.auth.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "카카오 로그인 요청")
public record KakaoLoginRequest(
        @Schema(description = "카카오 인가 코드", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "인가 코드는 필수입니다.")
        String code
) {}