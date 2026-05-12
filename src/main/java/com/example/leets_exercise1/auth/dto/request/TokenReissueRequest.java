package com.example.leets_exercise1.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenReissueRequest {
    @NotBlank(message = "refreshToken은 필수입니다.")
    private String refreshToken;
}
