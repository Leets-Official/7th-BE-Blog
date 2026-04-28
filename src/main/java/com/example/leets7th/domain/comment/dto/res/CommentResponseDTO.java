package com.example.leets7th.domain.comment.dto.res;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class CommentResponseDTO {
    @Builder
    public record CreateCommentResDTO(
            Long commentId,
            String content,
            String nickname,
            LocalDateTime createdAt
    ) {}

    @Builder
    public record ReplyResDTO(
            Long commentId,
            String content,
            String nickname,
            LocalDateTime createdAt
    ) {}

    @Builder
    public record CommentResDTO(
            Long commentId,
            String content,
            String nickname,
            LocalDateTime createdAt,
            List<ReplyResDTO> replies
    ) {}
}
