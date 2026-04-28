package com.example.demo.domain.report.dto;

import com.example.demo.domain.report.entity.ReportResolutionType;
import jakarta.validation.constraints.NotNull;

public record ReportResolveRequest(
        @NotNull(message = "resolverId는 필수입니다.")
        Long resolverId,

        @NotNull(message = "resolutionType은 필수입니다.")
        ReportResolutionType resolutionType
) {
}
