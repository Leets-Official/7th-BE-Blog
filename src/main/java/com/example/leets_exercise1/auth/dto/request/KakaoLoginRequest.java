package com.example.leets_exercise1.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoLoginRequest {
    @NotBlank(message = "인가 코드는 필수입니다.")
    private String code;

    public KakaoLoginRequest(String code) {
        this.code = code;
    }
}
