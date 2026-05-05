package com.example.demo.domain.comment.dto;

import com.example.demo.domain.comment.entity.CommentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "댓글 상태 응답")
public record CommentStatusResponse(
        @Schema(description = "댓글 ID", example = "1")
        Long commentId,
        @Schema(description = "댓글 상태", example = "ADOPTED")
        CommentStatus status
) {
}
