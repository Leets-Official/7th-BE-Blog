package com.example.demo.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
@Schema(description = "유저 생성 요청 DTO")
public class UserCreateRequest {

    @Schema(description = "유저 이름", example = "홍길동")
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @Schema(description = "이메일 주소", example = "test@example.com")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
}
