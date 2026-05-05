package com.example.demo.report.dto;

import com.example.demo.report.entity.Report;
import com.example.demo.report.entity.ReportStatus;
import com.example.demo.report.entity.ReportTargetType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "신고 응답 DTO")
public class ReportResponse {

    @Schema(description = "신고 ID", example = "1")
    private Long reportId;

    @Schema(description = "신고자 유저 ID", example = "5")
    private Long reporterId;

    @Schema(
            description = "신고 대상 타입 (POST: 게시글, COMMENT: 댓글)",
            example = "POST"
    )
    private ReportTargetType targetType;

    @Schema(
            description = "신고 대상 ID (게시글 ID 또는 댓글 ID)",
            example = "10"
    )
    private Long targetId;

    @Schema(
            description = "신고 사유",
            example = "욕설 및 비방 내용이 포함되어 있습니다."
    )
    private String reason;

    @Schema(
            description = "신고 상태 (PENDING: 처리 대기, RESOLVED: 처리 완료)",
            example = "PENDING"
    )
    private ReportStatus status;

    @Schema(description = "신고 생성 시간", example = "2026-05-05T12:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "신고 처리 완료 시간", example = "2026-05-05T13:00:00")
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
