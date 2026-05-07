package com.example.demo.controller;

import com.example.demo.domain.report.dto.ReportCreateRequest;
import com.example.demo.domain.report.dto.ReportCreateResponse;
import com.example.demo.domain.report.dto.ReportResolveRequest;
import com.example.demo.domain.report.dto.ReportResolveResponse;
import com.example.demo.domain.report.service.ReportService;
import com.example.demo.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/api/posts/{postId}/reports")
    public ResponseEntity<ApiResponse<ReportCreateResponse>> reportPost(
            @PathVariable Long postId,
            @Valid @RequestBody ReportCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "POST_REPORT_SUCCESS",
                        "게시글 신고 접수 성공",
                        reportService.reportPost(postId, request)
                ));
    }

    @PostMapping("/api/comments/{commentId}/reports")
    public ResponseEntity<ApiResponse<ReportCreateResponse>> reportComment(
            @PathVariable Long commentId,
            @Valid @RequestBody ReportCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "COMMENT_REPORT_SUCCESS",
                        "댓글 신고 접수 성공",
                        reportService.reportComment(commentId, request)
                ));
    }

    @PatchMapping("/api/reports/{reportId}/resolve")
    public ResponseEntity<ApiResponse<ReportResolveResponse>> resolveReport(
            @PathVariable Long reportId,
            @Valid @RequestBody ReportResolveRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "REPORT_RESOLVE_SUCCESS",
                        "신고 처리 완료",
                        reportService.resolveReport(reportId, request)
                )
        );
    }
}
