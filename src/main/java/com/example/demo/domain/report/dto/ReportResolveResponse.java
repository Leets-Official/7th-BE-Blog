package com.example.demo.domain.report.dto;

import com.example.demo.domain.report.entity.ReportResolutionType;
import com.example.demo.domain.report.entity.ReportStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "신고 처리 응답")
public record ReportResolveResponse(
        @Schema(description = "신고 ID", example = "1")
        Long reportId,
        @Schema(description = "신고 상태", example = "RESOLVED")
        ReportStatus status,
        @Schema(description = "적용된 처리 방식", example = "HIDE_COMMENT")
        ReportResolutionType resolutionType
) {
}
