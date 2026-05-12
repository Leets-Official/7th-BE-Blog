package com.example.demo.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "토큰 재발급 요청 DTO")
public class ReissueRequest {

    @Schema(description = "Refresh Token")
    @NotBlank(message = "refreshToken은 필수입니다.")
    private String refreshToken;
}