package com.example.demo.domain.report.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReportCreateRequest(
        @NotNull(message = "reporterId는 필수입니다.")
        Long reporterId,

        @NotBlank(message = "reason은 필수입니다.")
        String reason
) {
}
