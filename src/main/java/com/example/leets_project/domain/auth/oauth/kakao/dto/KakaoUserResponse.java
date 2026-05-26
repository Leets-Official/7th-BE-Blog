package com.example.leets_project.domain.auth.oauth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUserResponse(
        Long id,
        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount,
        Properties properties
) {
    public record KakaoAccount(String email, Profile profile) {}

    public record Profile(
            String nickname,
            @JsonProperty("profile_image_url")
            String profileImageUrl,
            @JsonProperty("thumbnail_image_url")
            String thumbnailImageUrl
    ) {}

    public record Properties(String nickname) {}
}