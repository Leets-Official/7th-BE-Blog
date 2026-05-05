package com.example.demo.domain.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "신고 생성 요청")
public record ReportCreateRequest(
        @Schema(description = "신고자 ID", example = "2")
        @NotNull(message = "reporterId는 필수입니다.")
        Long reporterId,

        @Schema(description = "신고 사유", example = "스팸성 게시물입니다.")
        @NotBlank(message = "reason은 필수입니다.")
        String reason
) {
}
