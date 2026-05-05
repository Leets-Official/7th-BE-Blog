package com.example.leets7th.domain.report.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReportRequest(
        @NotBlank(message = "신고 사유를 입력해주세요.")
        @Size(max = 255, message = "신고 사유는 255자 이하여야 합니다.")
        String reason
) {
}
