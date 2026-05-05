package com.example.demo.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 생성 응답")
public record PostCreateResponse(
        @Schema(description = "생성된 게시글 ID", example = "1")
        Long postId,
        @Schema(description = "결과 메시지", example = "게시글이 생성되었습니다.")
        String message
) {
}
