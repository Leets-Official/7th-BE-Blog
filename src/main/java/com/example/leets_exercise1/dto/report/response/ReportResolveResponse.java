package com.example.leets_exercise1.dto.report.response;

import com.example.leets_exercise1.domain.report.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportResolveResponse {
    private Integer reportId;
    private ReportStatus status;
}