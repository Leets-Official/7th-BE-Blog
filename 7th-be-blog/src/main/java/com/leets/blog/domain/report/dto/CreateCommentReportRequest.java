package com.leets.blog.domain.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCommentReportRequest(
        @Schema(description = "신고자 ID", example = "2")
        @NotNull(message = "신고자 ID는 필수입니다.")
        Long userId,

        @Schema(description = "댓글 신고 사유", example = "욕설이 포함되어 있습니다.")
        @NotBlank(message = "신고 사유는 필수입니다.")
        @Size(max = 500, message = "신고 사유는 500자 이내여야 합니다.")
        String reason
) {
}
