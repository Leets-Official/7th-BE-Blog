package com.example.leets_exercise1.dto.report.response;

import com.example.leets_exercise1.domain.report.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReportCreateResponse {
    private Integer reportId;
    private String targetType;
    private Integer targetId;
    private ReportStatus status;
    private LocalDateTime createdAt;
}