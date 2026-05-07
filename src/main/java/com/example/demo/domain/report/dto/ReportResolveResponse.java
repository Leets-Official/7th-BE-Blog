package com.example.demo.domain.report.dto;

import com.example.demo.domain.report.entity.ReportResolutionType;
import com.example.demo.domain.report.entity.ReportStatus;

public record ReportResolveResponse(
        Long reportId,
        ReportStatus status,
        ReportResolutionType resolutionType
) {
}
