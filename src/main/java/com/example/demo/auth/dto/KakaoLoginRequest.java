package com.example.demo.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "카카오 로그인 요청 DTO")
public class KakaoLoginRequest {

    @Schema(
            description = "카카오 인가 코드",
            example = "A1b2C3d4E5f6G7h8"
    )
    @NotBlank(message = "인가 코드는 필수입니다.")
    private String code;
}