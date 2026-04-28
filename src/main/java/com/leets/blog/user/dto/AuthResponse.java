package com.leets.blog.user.dto;

import com.leets.blog.user.domain.User;
import com.leets.blog.user.domain.UserRole;
import lombok.Getter;

public class AuthResponse {

    @Getter
    public static class UserInfo {
        private final Long userId;
        private final String email;
        private final String nickname;
        private final UserRole role;

        public UserInfo(User user) {
            this.userId = user.getId();
            this.email = user.getEmail();
            this.nickname = user.getNickname();
            this.role = user.getRole();
        }
    }

    @Getter
    public static class Login {
        private final Long userId;
        private final String email;
        private final String nickname;
        private final UserRole role;
        private final String authHeaderName;

        public Login(User user) {
            this.userId = user.getId();
            this.email = user.getEmail();
            this.nickname = user.getNickname();
            this.role = user.getRole();
            this.authHeaderName = "X-USER-ID";
        }
    }
}
