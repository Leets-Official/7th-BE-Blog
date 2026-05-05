package com.example.demo.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "댓글 채택 요청")
public record CommentAdoptRequest(
        @Schema(description = "채택을 요청한 게시글 작성자 ID", example = "1")
        @NotNull(message = "userId는 필수입니다.")
        Long userId
) {
}
