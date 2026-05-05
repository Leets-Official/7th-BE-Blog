package com.example.demo.report.dto;

import com.example.demo.report.entity.Report;
import com.example.demo.report.entity.ReportStatus;
import com.example.demo.report.entity.ReportTargetType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReportResponse {

    private Long reportId;
    private Long reporterId;
    private ReportTargetType targetType;
    private Long targetId;
    private String reason;
    private ReportStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;

    public static ReportResponse from(Report report) {
        Long targetId = null;

        if (report.getTargetType() == ReportTargetType.POST) {
            targetId = report.getPost().getId();
        }

        if (report.getTargetType() == ReportTargetType.COMMENT) {
            targetId = report.getComment().getId();
        }

        return ReportResponse.builder()
                .reportId(report.getId())
                .reporterId(report.getReporter().getId())
                .targetType(report.getTargetType())
                .targetId(targetId)
                .reason(report.getReason())
                .status(report.getStatus())
                .createdAt(report.getCreatedAt())
                .resolvedAt(report.getResolvedAt())
                .build();
    }
}
