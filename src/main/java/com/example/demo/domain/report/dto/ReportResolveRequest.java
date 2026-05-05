package com.example.demo.domain.report.dto;

import com.example.demo.domain.report.entity.ReportResolutionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "신고 처리 요청")
public record ReportResolveRequest(
        @Schema(description = "신고 처리자 ID", example = "1")
        @NotNull(message = "resolverId는 필수입니다.")
        Long resolverId,

        @Schema(description = "신고 처리 방식", example = "HIDE_COMMENT")
        @NotNull(message = "resolutionType은 필수입니다.")
        ReportResolutionType resolutionType
) {
}
