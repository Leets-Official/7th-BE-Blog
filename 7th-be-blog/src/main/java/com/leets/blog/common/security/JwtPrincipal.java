package com.leets.blog.common.security;

public record JwtPrincipal(
        Long userId,
        String email,
        String role
) {
}
