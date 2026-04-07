package com.example.blog.domain.report.dto;

import com.example.blog.domain.report.entity.Report;

import java.time.LocalDateTime;

public record ReportResponse(
    Long reportId,
    Long reporterId,
    String reporterUsername,
    String targetType,
    Long targetId,
    String reason,
    LocalDateTime createdAt
) {

    public static ReportResponse from(Report report) {
        String targetType = report.getPost() != null ? "POST" : "COMMENT";
        Long targetId = report.getPost() != null
            ? report.getPost().getId()
            : report.getComment().getId();
        return new ReportResponse(
            report.getId(),
            report.getReporter().getId(),
            report.getReporter().getUsername(),
            targetType,
            targetId,
            report.getReason(),
            report.getCreatedAt()
        );
    }
}
