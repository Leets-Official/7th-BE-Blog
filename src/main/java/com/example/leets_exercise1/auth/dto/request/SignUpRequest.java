package com.example.leets_exercise1.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(max = 255, message = "닉네임은 255자 이하로 입력해주세요.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 255, message = "비밀번호는 8자 이상 255자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 255, message = "이름은 255자 이하로 입력해주세요.")
    private String name;

    @NotNull(message = "나이는 필수입니다.")
    @PositiveOrZero(message = "나이는 0 이상이어야 합니다.")
    private Integer age;
}
