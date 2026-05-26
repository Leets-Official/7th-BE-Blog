package com.example.leets7th.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUserInfoResponse(
        Long id,
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount
) {
    public record KakaoAccount(
            String email,
            KakaoProfile profile
    ) {
    }

    public record KakaoProfile(
            String nickname
    ) {
    }

    public String email() {
        if (kakaoAccount != null && kakaoAccount.email() != null) {
            return kakaoAccount.email();
        }
        return "kakao_" + id + "@kakao.com";
    }

    public String nickname() {
        if (kakaoAccount != null && kakaoAccount.profile() != null
                && kakaoAccount.profile().nickname() != null) {
            return kakaoAccount.profile().nickname();
        }
        return "kakao_" + id;
    }
}
