package com.example.week2.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KakaoResponse {

    public record TokenResponse(
            @JsonProperty("access_token")
            String accessToken,

            @JsonProperty("token_type")
            String tokenType,

            @JsonProperty("refresh_token")
            String refreshToken


    ) {}

    public record KakaoUserResponse(
            Long id,
            @JsonProperty("kakao_account")
            KakaoAccount kakaoAccount
    ) {}


    public record KakaoAccount(
            String email,
            Profile profile
    ) {}

    public record Profile(
            String nickname
    ) {}

    public record KakaoUserInfo(
            String providerId,
            String email,
            String nickname
    ) {}
}
