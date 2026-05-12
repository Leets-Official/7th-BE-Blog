package com.example.leets_exercise1.auth.dto.response;

import com.example.leets_exercise1.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpResponse {
    private Long userId;
    private String email;
    private String nickname;

    public static SignUpResponse from(User user) {
        return SignUpResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}
