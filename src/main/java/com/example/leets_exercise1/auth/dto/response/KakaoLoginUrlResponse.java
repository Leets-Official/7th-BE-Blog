package com.example.leets_exercise1.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoLoginUrlResponse {
    private String loginUrl;
}
