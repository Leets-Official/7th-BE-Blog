package com.leets.blog.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leets.blog.user.domain.User;
import com.leets.blog.user.domain.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class AuthResponse {

    @Getter
    public static class UserInfo {
        @Schema(description = "유저 ID", example = "1")
        private final Long userId;
        @Schema(description = "이메일", example = "test@example.com")
        private final String email;
        @Schema(description = "닉네임", example = "yukyoung")
        private final String nickname;
        @Schema(description = "권한", example = "USER")
        private final UserRole role;

        public UserInfo(Long userId, String email, String nickname, UserRole role) {
            this.userId = userId;
            this.email = email;
            this.nickname = nickname;
            this.role = role;
        }

        public UserInfo(User user) {
            this.userId = user.getId();
            this.email = user.getEmail();
            this.nickname = user.getNickname();
            this.role = user.getRole();
        }
    }

    @Getter
    public static class Login {
        @Schema(description = "유저 ID", example = "1")
        private final Long userId;
        @Schema(description = "이메일", example = "test@example.com")
        private final String email;
        @Schema(description = "닉네임", example = "yukyoung")
        private final String nickname;
        @Schema(description = "권한", example = "USER")
        private final UserRole role;
        @JsonIgnore
        @Schema(description = "Access token")
        private final String accessToken;
        @JsonIgnore
        @Schema(description = "Refresh token")
        private final String refreshToken;

        public Login(User user, String accessToken, String refreshToken) {
            this.userId = user.getId();
            this.email = user.getEmail();
            this.nickname = user.getNickname();
            this.role = user.getRole();
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}
