package com.leets.blog.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthRequest {

    @Getter
    @NoArgsConstructor
    public static class SignUp {
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        @Schema(description = "이메일", example = "test@example.com")
        private String email;

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, max = 100, message = "비밀번호는 8자 이상 100자 이하여야 합니다.")
        @Schema(description = "비밀번호(8~100자)", example = "password1234")
        private String password;

        @NotBlank(message = "닉네임은 필수입니다.")
        @Size(max = 100, message = "닉네임은 100자 이하여야 합니다.")
        @Schema(description = "닉네임", example = "yukyoung")
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    public static class Login {
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        @Schema(description = "이메일", example = "test@example.com")
        private String email;

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Schema(description = "비밀번호", example = "password1234")
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoLogin {
        @NotBlank(message = "카카오 인가 코드는 필수입니다.")
        @Schema(description = "카카오 인가 코드", example = "authorization-code-from-kakao")
        private String code;
    }
}
