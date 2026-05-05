package com.example.demo.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "게시글 삭제 요청")
public record PostDeleteRequest(
        @Schema(description = "삭제를 요청한 사용자 ID", example = "1")
        @NotNull(message = "userId는 필수입니다.")
        Long userId
) {
}
