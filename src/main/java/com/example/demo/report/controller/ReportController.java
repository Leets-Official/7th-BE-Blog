package com.example.demo.report.controller;

import com.example.demo.global.exception.ApiResponse;
import com.example.demo.global.exception.BaseCode;
import com.example.demo.global.exception.ResponseUtil;
import com.example.demo.report.dto.ReportCreateRequest;
import com.example.demo.report.dto.ReportResponse;
import com.example.demo.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Report", description = "신고 관련 API")
@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // 게시글 신고
    @Operation(
            summary = "게시글 신고 API",
            description = "postId에 해당하는 게시글을 신고합니다."
    )
    @PostMapping("/posts/{postId}/reports")
    public ResponseEntity<ApiResponse<ReportResponse>> reportPost(
            @Parameter(description = "신고할 게시글 ID", example = "1")
            @PathVariable Long postId,

            @RequestBody ReportCreateRequest request
    ) {
        ReportResponse response = reportService.reportPost(postId, request);

        return ResponseEntity.ok(
                ResponseUtil.success(BaseCode.REPORT_POST_SUCCESS, response)
        );
    }

    // 댓글 신고
    @Operation(
            summary = "댓글 신고 API",
            description = "commentId에 해당하는 댓글을 신고합니다."
    )
    @PostMapping("/comments/{commentId}/reports")
    public ResponseEntity<ApiResponse<ReportResponse>> reportComment(
            @Parameter(description = "신고할 댓글 ID", example = "1")
            @PathVariable Long commentId,

            @RequestBody ReportCreateRequest request
    ) {
        ReportResponse response = reportService.reportComment(commentId, request);

        return ResponseEntity.ok(
                ResponseUtil.success(BaseCode.REPORT_COMMENT_SUCCESS, response)
        );
    }

    // 신고 처리 완료
    @Operation(
            summary = "신고 처리 완료 API",
            description = "reportId에 해당하는 신고 상태를 처리 완료로 변경합니다."
    )
    @PatchMapping("/reports/{reportId}/resolve")
    public ResponseEntity<ApiResponse<ReportResponse>> resolveReport(
            @Parameter(description = "처리할 신고 ID", example = "1")
            @PathVariable Long reportId
    ) {
        ReportResponse response = reportService.resolveReport(reportId);

        return ResponseEntity.ok(
                ResponseUtil.success(BaseCode.REPORT_RESOLVE_SUCCESS, response)
        );
    }
}