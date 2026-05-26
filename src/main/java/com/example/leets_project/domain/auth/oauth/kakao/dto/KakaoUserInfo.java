package com.example.leets_project.domain.auth.oauth.kakao.dto;

public record KakaoUserInfo(
        String providerId,
        String email,
        String nickname,
        String profileImageUrl
) { // 개인정보 제공 동의 여부에 따른 null 값 방지
    public static KakaoUserInfo from(KakaoUserResponse response) {
        KakaoUserResponse.KakaoAccount account = response.kakaoAccount();
        KakaoUserResponse.Profile profile = account != null ? account.profile() : null;

        String nickname = profile != null ? profile.nickname() : null;
        String profileImageUrl = profile != null ? profile.profileImageUrl() : null;

        if (nickname == null && response.properties() != null) {
            nickname = response.properties().nickname();
        }

        return new KakaoUserInfo(
                String.valueOf(response.id()),
                account != null ? account.email() : null,
                nickname,
                profileImageUrl
        );
    }
}