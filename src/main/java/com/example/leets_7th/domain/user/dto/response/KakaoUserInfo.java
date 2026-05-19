package com.example.leets_7th.domain.user.dto.response;

import java.util.Map;

public record KakaoUserInfo(
        String kakaoId,
        String email,
        String nickname
) {
    public static KakaoUserInfo from(Map<String, Object> body) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) body.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        String email = kakaoAccount.get("email") != null
                ? (String) kakaoAccount.get("email")
                : body.get("id") + "@kakao.com";

        return new KakaoUserInfo(
                String.valueOf(body.get("id")),
                email,
                (String) profile.get("nickname")
        );
    }
}
