package com.example.leets_exercise1.auth.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoResponse {
    private Long id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @NoArgsConstructor
    public static class KakaoAccount {
        private String email;
        private Profile profile;
    }

    @Getter
    @NoArgsConstructor
    public static class Profile {
        private String nickname;
    }

    public String getEmail() {
        if (kakaoAccount == null || kakaoAccount.getEmail() == null) {
            return "kakao_" + id + "@kakao.local";
        }
        return kakaoAccount.getEmail();
    }

    public String getNickname() {
        if (kakaoAccount == null || kakaoAccount.getProfile() == null || kakaoAccount.getProfile().getNickname() == null) {
            return "kakao_" + id;
        }
        return kakaoAccount.getProfile().getNickname();
    }
}
