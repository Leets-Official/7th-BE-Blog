package com.leets.blog.domain.report.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePostReportRequest(
        @NotNull(message = "신고자 ID는 필수입니다.")
        Long userId,

        @NotBlank(message = "신고 사유는 필수입니다.")
        @Size(max = 500, message = "신고 사유는 500자 이내여야 합니다.")
        String reason
) {
}
