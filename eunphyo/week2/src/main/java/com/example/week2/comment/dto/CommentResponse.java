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
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){}

    @Builder
    public record CommentReportResponse(
            Long reportId,
            Long commentId,
            String reason,
            String status,
            LocalDateTime createdAt
    ) {}

    @Builder
    public record CommentReportResolve(
            Long reportId,
            Long commentId,
            String reason,
            String status,
            LocalDateTime createdAt
    ) {}
}
