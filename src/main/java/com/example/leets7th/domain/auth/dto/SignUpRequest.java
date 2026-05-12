package com.example.leets7th.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank @Email
        String email,

        @NotBlank @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
        String password,

        @NotBlank @Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하이어야 합니다.")
        String nickname
) {
}
