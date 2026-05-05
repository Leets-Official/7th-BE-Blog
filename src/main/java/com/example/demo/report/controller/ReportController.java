package com.example.demo.report.controller;

import com.example.demo.global.exception.ApiResponse;
import com.example.demo.global.exception.BaseCode;
import com.example.demo.global.exception.ResponseUtil;
import com.example.demo.report.dto.ReportCreateRequest;
import com.example.demo.report.dto.ReportResponse;
import com.example.demo.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // 게시글 신고
    @PostMapping("/posts/{postId}/reports")
    public ResponseEntity<ApiResponse<ReportResponse>> reportPost(
            @PathVariable Long postId,
            @RequestBody ReportCreateRequest request
    ) {
        ReportResponse response = reportService.reportPost(postId, request);

        return ResponseEntity.ok(
                ResponseUtil.success(BaseCode.REPORT_POST_SUCCESS, response)
        );
    }

    // 댓글 신고
    @PostMapping("/comments/{commentId}/reports")
    public ResponseEntity<ApiResponse<ReportResponse>> reportComment(
            @PathVariable Long commentId,
            @RequestBody ReportCreateRequest request
    ) {
        ReportResponse response = reportService.reportComment(commentId, request);

        return ResponseEntity.ok(
                ResponseUtil.success(BaseCode.REPORT_COMMENT_SUCCESS, response)
        );
    }

    // 신고 처리 완료
    @PatchMapping("/reports/{reportId}/resolve")
    public ResponseEntity<ApiResponse<ReportResponse>> resolveReport(
            @PathVariable Long reportId
    ) {
        ReportResponse response = reportService.resolveReport(reportId);

        return ResponseEntity.ok(
                ResponseUtil.success(BaseCode.REPORT_RESOLVE_SUCCESS, response)
        );
    }
}