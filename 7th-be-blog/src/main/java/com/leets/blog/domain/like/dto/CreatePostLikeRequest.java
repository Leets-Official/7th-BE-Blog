package com.leets.blog.domain.like.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreatePostLikeRequest(
        @Schema(description = "좋아요를 누르는 사용자 ID", example = "1")
        @NotNull(message = "사용자 ID는 필수입니다.")
        Long userId
) {
}
