package com.example.leets_7th.domain.user.dto.request;

import com.example.leets_7th.domain.user.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(

        @NotBlank(message = "이름을 입력해주세요.")
        String name,

        @NotNull(message = "성별을 입력해주세요.")
        Gender gender,

        @Email(message = "이메일 형식이 올바르지 않습니다.")
        @NotBlank(message = "이메일을 입력해주세요.")
        String email,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password,

        @Min(value = 0, message = "나이는 0 이상이어야 합니다.")
        int age
) {
}
