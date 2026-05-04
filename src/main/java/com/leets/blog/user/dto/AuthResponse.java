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
        private final Long userId;
        private final String email;
        private final String nickname;
        private final UserRole role;

        public Login(User user) {
            this.userId = user.getId();
            this.email = user.getEmail();
            this.nickname = user.getNickname();
            this.role = user.getRole();
        }
    }
}
