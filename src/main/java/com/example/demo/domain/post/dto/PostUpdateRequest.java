package com.example.demo.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "게시글 수정 요청")
public record PostUpdateRequest(
        @Schema(description = "수정을 요청한 사용자 ID", example = "1")
        @NotNull(message = "userId는 필수입니다.")
        Long userId,

        @Schema(description = "수정할 제목", example = "수정된 제목")
        String title,
        @Schema(description = "수정할 내용", example = "수정된 내용")
        String content,
        @Schema(description = "수정할 이미지 URL", example = "https://example.com/updated-image.png")
        String imageUrl
) {
}
