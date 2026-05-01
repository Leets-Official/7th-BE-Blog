package com.example.week2.report.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class ReportResponse {

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
