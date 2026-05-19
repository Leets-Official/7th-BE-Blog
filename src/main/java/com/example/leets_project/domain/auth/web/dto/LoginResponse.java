package com.example.leets_project.domain.auth.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "로그인 성공 응답")
public record LoginResponse(
        @Schema(description = "토큰 정보")
        TokenResponse token,

        @Schema(description = "사용자 정보")
        UserInfoResponse user
) {
    public record UserInfoResponse(
            @Schema(description = "사용자 고유 ID", example = "1")
            Long id,
            @Schema(description = "이메일", example = "test@example.com")
            String email,
            @Schema(description = "닉네임", example = "리츠화이팅")
            String nickname
    ) {}
}