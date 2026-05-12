package com.leets.blog.domain.report.dto;

import com.leets.blog.domain.report.entity.ReportStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "게시글 신고 응답")
public class PostReportResponse {

    @Schema(description = "신고 ID", example = "1")
    private Long reportId;
    @Schema(description = "신고된 게시글 ID", example = "10")
    private Long postId;
    @Schema(description = "신고자 ID", example = "2")
    private Long reporterId;
    @Schema(description = "신고 사유", example = "광고성 게시글입니다.")
    private String reason;
    @Schema(description = "신고 처리 상태", example = "PENDING")
    private ReportStatus status;
    @Schema(description = "신고 생성 시각", example = "2026-05-05T14:30:00")
    private LocalDateTime createdAt;
}
