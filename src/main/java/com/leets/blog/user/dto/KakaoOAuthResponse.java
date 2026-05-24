package com.leets.blog.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class KakaoOAuthResponse {

    @Getter
    public static class Token {
        @JsonProperty("access_token")
        private String accessToken;
    }

    @Getter
    public static class UserInfo {
        private Long id;

        @JsonProperty("kakao_account")
        private KakaoAccount kakaoAccount;

        public String getEmail() {
            return kakaoAccount == null ? null : kakaoAccount.getEmail();
        }

        public String getNickname() {
            if (kakaoAccount == null || kakaoAccount.getProfile() == null) {
                return null;
            }
            return kakaoAccount.getProfile().getNickname();
        }
    }

    @Getter
    public static class KakaoAccount {
        private String email;
        private Profile profile;
    }

    @Getter
    public static class Profile {
        private String nickname;
    }
}
