package com.example.demo.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "회원가입 요청")
public record SignUpRequest(
        @Schema(description = "이메일", example = "user@example.com")
        @NotBlank(message = "email은 필수입니다.")
        @Email(message = "올바른 이메일 형식이어야 합니다.")
        String email,

        @Schema(description = "닉네임", example = "tester")
        @NotBlank(message = "nickname은 필수입니다.")
        @Size(max = 10, message = "nickname은 10자 이하여야 합니다.")
        String nickname,

        @Schema(description = "비밀번호", example = "password123!")
        @NotBlank(message = "password는 필수입니다.")
        @Size(min = 8, message = "password는 8자 이상이어야 합니다.")
        String password
) {
}
