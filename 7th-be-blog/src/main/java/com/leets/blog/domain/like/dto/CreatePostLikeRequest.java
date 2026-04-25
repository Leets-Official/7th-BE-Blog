package com.leets.blog.domain.like.dto;

import jakarta.validation.constraints.NotNull;

public record CreatePostLikeRequest(
        @NotNull(message = "사용자 ID는 필수입니다.")
        Long userId
) {
}
