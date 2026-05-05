package com.example.demo.domain.report.dto;

import com.example.demo.domain.report.entity.ReportStatus;
import com.example.demo.domain.report.entity.ReportTargetType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "신고 생성 응답")
public record ReportCreateResponse(
        @Schema(description = "생성된 신고 ID", example = "1")
        Long reportId,
        @Schema(description = "신고 대상 타입", example = "POST")
        ReportTargetType targetType,
        @Schema(description = "신고 상태", example = "PENDING")
        ReportStatus status
) {
}
