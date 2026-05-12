package com.example.leets7th.domain.report.controller;

import com.example.leets7th.domain.report.dto.ReportRequest;
import com.example.leets7th.domain.report.service.ReportService;
import com.example.leets7th.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReportController implements ReportControllerDocs {

    private final ReportService reportService;

    @PostMapping("/api/posts/{postId}/reports")
    public ResponseEntity<ApiResponse<Map<String, Object>>> reportPost(
            @PathVariable Long postId,
            @RequestBody @Valid ReportRequest request,
            @AuthenticationPrincipal Long userId
    ) {
        Long reportId = reportService.reportPost(postId, request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(Map.of("reportId", reportId, "message", "게시글이 신고되었습니다.")));
    }

    @PostMapping("/api/comments/{commentId}/reports")
    public ResponseEntity<ApiResponse<Map<String, Object>>> reportComment(
            @PathVariable Long commentId,
            @RequestBody @Valid ReportRequest request,
            @AuthenticationPrincipal Long userId
    ) {
        Long reportId = reportService.reportComment(commentId, request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(Map.of("reportId", reportId, "message", "댓글이 신고되었습니다.")));
    }

    @PatchMapping("/api/reports/{reportId}/resolve")
    public ResponseEntity<ApiResponse<Map<String, String>>> resolveReport(@PathVariable Long reportId) {
        reportService.resolveReport(reportId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("message", "신고가 처리되었습니다.")));
    }
}
