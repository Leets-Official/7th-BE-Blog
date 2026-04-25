package com.leets.blog.domain.report.dto;

import com.leets.blog.domain.report.entity.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CommentReportResponse {

    private Long reportId;
    private Long commentId;
    private Long reporterId;
    private String reason;
    private ReportStatus status;
    private LocalDateTime createdAt;
}
