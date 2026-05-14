package com.example.week2.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReissueRequest {

    @Schema(description = "Refresh Token")
    @NotBlank(message = "Refresh Token을 입력해주세요.")
    private String refreshToken;
}