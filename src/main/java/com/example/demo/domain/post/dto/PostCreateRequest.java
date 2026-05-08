package com.example.demo.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "게시글 생성 요청")
public record PostCreateRequest(
        @Schema(description = "작성자 사용자 ID", example = "1")
        @NotNull(message = "userId는 필수입니다.")
        Long userId,

        @Schema(description = "게시글 제목", example = "첫 번째 게시글")
        @NotBlank(message = "title은 필수입니다.")
        String title,

        @Schema(description = "게시글 내용", example = "게시글 내용입니다.")
        @NotBlank(message = "content는 필수입니다.")
        String content,

        @Schema(description = "대표 이미지 URL", example = "https://example.com/image.png")
        String imageUrl
) {
}
