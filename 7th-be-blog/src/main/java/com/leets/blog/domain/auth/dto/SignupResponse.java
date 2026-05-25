package com.leets.blog.domain.auth.dto;

import com.leets.blog.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record SignupResponse(
        @Schema(description = "사용자 ID", example = "1")
        Long id,

        @Schema(description = "이메일", example = "user@example.com")
        String email,

        @Schema(description = "이름", example = "김동빈")
        String name,

        @Schema(description = "닉네임", example = "dongbin807")
        String nickname
) {

    public static SignupResponse from(User user) {
        return new SignupResponse(user.getId(), user.getEmail(), user.getName(), user.getNickname());
    }
}
