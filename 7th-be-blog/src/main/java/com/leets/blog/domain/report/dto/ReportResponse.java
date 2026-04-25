package com.leets.blog.domain.report.dto;

import com.leets.blog.domain.report.entity.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ReportResponse {

    private Long reportId;
    private Long postId;
    private Long reporterId;
    private String reason;
    private ReportStatus status;
    private LocalDateTime createdAt;
}
