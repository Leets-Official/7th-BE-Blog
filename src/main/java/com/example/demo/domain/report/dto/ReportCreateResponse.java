package com.example.demo.domain.report.dto;

import com.example.demo.domain.report.entity.ReportStatus;
import com.example.demo.domain.report.entity.ReportTargetType;

public record ReportCreateResponse(
        Long reportId,
        ReportTargetType targetType,
        ReportStatus status
) {
}
