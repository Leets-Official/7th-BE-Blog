package com.example.week2.report.controller;

import com.example.week2.global.response.ApiResponse;
import com.example.week2.global.response.ErrorCode;
import com.example.week2.global.swagger.ApiErrorCodeExample;
import com.example.week2.report.dto.ReportRequest;
import com.example.week2.report.dto.ReportResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Report", description = "신고 API")
public interface ReportControllerDocs {

        @Operation(summary = "댓글 신고", description = "특정 댓글을 신고합니다.")
        @ApiErrorCodeExample({
                ErrorCode.USER_NOT_FOUND,
                ErrorCode.REPORT_COMMENT_ALREADY_RESOLVED
        })
        @PostMapping
        ResponseEntity<ApiResponse<ReportResponse.CommentReportResponse>> reportComment(
                @Parameter(description = "게시글 post ID", example = "1")
                @PathVariable Long postId,
                @Parameter(description = "댓글 comment ID", example = "1")
                @PathVariable Long commentId,
                @Valid @RequestBody ReportRequest request
        );

        @Operation(summary = "댓글 신고 처리", description = "특정 댓글 신고를 처리합니다.")
        @ApiErrorCodeExample({
                ErrorCode.REPORT_NOT_FOUND,
                ErrorCode.REPORT_COMMENT_ALREADY_RESOLVED
        })
        @PatchMapping
        ResponseEntity<ApiResponse<ReportResponse.CommentReportResolve>> resolveReport(
                @Parameter(description = "게시글 post ID", example = "1")
                @PathVariable Long postId,
                @Parameter(description = "댓글 comment ID", example = "1")
                @PathVariable Long commentId,
                @Parameter(description = "신고 report ID", example = "1")
                @PathVariable Long reportId
        );
    }
