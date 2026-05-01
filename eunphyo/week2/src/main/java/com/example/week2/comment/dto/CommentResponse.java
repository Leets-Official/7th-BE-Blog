package com.example.week2.comment.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class CommentResponse {
    @Builder
    public record CreateCommentResponse(
            Long commentId,
            String content,
            String nickname,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Builder
    public record CommentDetailResponse (
            Long commentId,
            String content,
            String nickname,
            int likeCount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){}

    @Builder
    public record CommentLikeResponse(
            Long commentId,
            Long userId,
            int likeCount
    ) {}
}
