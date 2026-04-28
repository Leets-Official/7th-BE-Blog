package com.example.leets7th.domain.comment.dto.res;

import lombok.Builder;

import java.time.LocalDateTime;

public class CommentResponseDTO {
    @Builder
    public record CreateCommentResDTO(
            Long commentId,
            String content,
            String nickname,
            LocalDateTime createdAt
    ) {}
}
