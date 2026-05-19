package com.example.blog.domain.auth.dto;

public record KakaoUserInfo(
    Long id,
    KakaoAccount kakaoAccount
) {
    public record KakaoAccount(
        String email,
        Profile profile
    ) {
        public record Profile(
            String nickname,
            String profileImageUrl
        ) {}
    }

    public String email() {
        return kakaoAccount != null ? kakaoAccount.email() : null;
    }

    public String nickname() {
        if (kakaoAccount == null || kakaoAccount.profile() == null) {
            return null;
        }
        return kakaoAccount.profile().nickname();
    }

    public String profileImageUrl() {
        if (kakaoAccount == null || kakaoAccount.profile() == null) {
            return null;
        }
        return kakaoAccount.profile().profileImageUrl();
    }
}
