package com.example.week2.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthRequest {
    @Getter
    @NoArgsConstructor
    public static class SignupRequest {
        @NotBlank(message = "이름을 입력해주세요.")
        @Size(max = 50, message = "이름은 최대 50자까지 가능합니다.")
        private String name;

        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(max = 50, message = "닉네임은 최대 50자까지 가능합니다.")
        private String nickname;

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min =8, max = 100, message = "비밀번호는 최소 8자, 최대 100자입니다.")
        private String password;
    }

    @Getter
    @NoArgsConstructor
    public static class LoginRequest {

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min =8, max = 100, message = "비밀번호는 최소 8자, 최대 100자입니다.")
        private String password;
    }

    @Getter
    @NoArgsConstructor
    public static class ReissueRequest {

        @NotBlank(message = "refresh token을 입력해주세요.")
        private String refreshToken;
    }
}
