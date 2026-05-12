package com.leets.blog.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @Schema(description = "이메일", example = "user@example.com")
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,

        @Schema(description = "비밀번호", example = "password1234")
        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(max = 100, message = "비밀번호는 100자 이내여야 합니다.")
        String password,

        @JsonAlias("nickname")
        @Schema(description = "닉네임", example = "tester")
        @NotBlank(message = "닉네임은 필수입니다.")
        @Size(max = 30, message = "닉네임은 30자 이내여야 합니다.")
        String name
) {
}
