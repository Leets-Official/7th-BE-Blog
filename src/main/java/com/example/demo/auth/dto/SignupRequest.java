package com.example.demo.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "회원가입 요청 DTO")
public class SignupRequest {

    @Schema(description = "이름", example = "홍길동")
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @Schema(description = "닉네임", example = "자취왕")
    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    @Schema(description = "이메일", example = "test@example.com")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @Schema(description = "비밀번호", example = "password1234")
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
