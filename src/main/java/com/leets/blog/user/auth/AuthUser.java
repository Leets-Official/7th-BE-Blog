package com.leets.blog.user.auth;

import com.leets.blog.user.domain.User;
import com.leets.blog.user.domain.UserRole;
import lombok.Getter;

@Getter
public class AuthUser {
    private final Long userId;
    private final String email;
    private final String nickname;
    private final UserRole role;

    public AuthUser(Long userId, String email, String nickname, UserRole role) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
    }

    public static AuthUser from(User user) {
        return new AuthUser(user.getId(), user.getEmail(), user.getNickname(), user.getRole());
    }
}
