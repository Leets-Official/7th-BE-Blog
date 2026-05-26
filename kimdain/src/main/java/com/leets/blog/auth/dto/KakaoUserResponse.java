package com.leets.blog.auth.dto;

public record KakaoUserResponse(
        Long id,
        KakaoProperties properties
) {
    public record KakaoProperties(
            String nickname
    ) {}
}