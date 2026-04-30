package com.example.leets_exercise1.controller;

import com.example.leets_exercise1.common.response.ApiResponse;
import com.example.leets_exercise1.dto.report.request.CommentReportCreateRequest;
import com.example.leets_exercise1.dto.report.request.PostReportCreateRequest;
import com.example.leets_exercise1.dto.report.response.ReportCreateResponse;
import com.example.leets_exercise1.dto.report.response.ReportResolveResponse;
import com.example.leets_exercise1.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/posts/{postId}/reports")
    public ResponseEntity<ApiResponse<ReportCreateResponse>> reportPost(
            @PathVariable Long postId,
            @Valid @RequestBody PostReportCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("2011", "게시물 신고 성공", reportService.reportPost(postId, request)));
    }

    @PostMapping("/comments/{commentId}/reports")
    public ResponseEntity<ApiResponse<ReportCreateResponse>> reportComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentReportCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("2012", "댓글 신고 성공", reportService.reportComment(commentId, request)));
    }

    @PostMapping("/reports/{reportId}/resolve")
    public ResponseEntity<ApiResponse<ReportResolveResponse>> resolveReport(@PathVariable Long reportId) {
        return ResponseEntity.ok(
                ApiResponse.success("2004", "신고 처리 완료", reportService.resolveReport(reportId))
        );
    }

    @GetMapping("/report-test")
    public String reportTest() {
        return "report controller ok";
    }
}