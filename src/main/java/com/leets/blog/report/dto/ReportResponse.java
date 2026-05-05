package com.leets.blog.report.dto;

import com.leets.blog.report.domain.Report;
import com.leets.blog.report.domain.ReportStatus;
import com.leets.blog.report.domain.ReportTargetType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReportResponse {
    @Schema(description = "신고 ID", example = "1")
    private final Long id;
    @Schema(description = "신고 대상 타입", example = "POST")
    private final ReportTargetType targetType;
    @Schema(description = "신고 대상 ID(게시글/댓글 ID)", example = "10")
    private final Long targetId;
    @Schema(description = "신고자 사용자 ID", example = "1")
    private final Long reporterId;
    @Schema(description = "처리자 사용자 ID(없으면 null)", example = "2")
    private final Long resolverId;
    @Schema(description = "신고 사유", example = "욕설/비방이 포함되어 있어요.")
    private final String reason;
    @Schema(description = "신고 상태", example = "PENDING")
    private final ReportStatus status;
    @Schema(description = "처리 시각(처리 전이면 null)", example = "2026-05-06T00:30:00")
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
