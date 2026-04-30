package com.example.leets_exercise1.dto.report.response;

import com.example.leets_exercise1.domain.report.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReportCreateResponse {
    private Long reportId;
    private String targetType;
    private Long targetId;
    private ReportStatus status;
    private LocalDateTime createdAt;
}