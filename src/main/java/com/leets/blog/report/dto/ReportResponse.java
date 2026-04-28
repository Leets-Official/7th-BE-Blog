package com.leets.blog.report.dto;

import com.leets.blog.report.domain.Report;
import com.leets.blog.report.domain.ReportStatus;
import com.leets.blog.report.domain.ReportTargetType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReportResponse {
    private final Long id;
    private final ReportTargetType targetType;
    private final Long targetId;
    private final Long reporterId;
    private final Long resolverId;
    private final String reason;
    private final ReportStatus status;
    private final LocalDateTime resolvedAt;

    public ReportResponse(Report report) {
        this.id = report.getId();
        this.targetType = report.getTargetType();
        this.targetId = report.getTargetId();
        this.reporterId = report.getReporter().getId();
        this.resolverId = report.getResolver() == null ? null : report.getResolver().getId();
        this.reason = report.getReason();
        this.status = report.getStatus();
        this.resolvedAt = report.getResolvedAt();
    }
}
