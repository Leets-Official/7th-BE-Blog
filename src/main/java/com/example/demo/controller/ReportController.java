package com.example.demo.controller;

import com.example.demo.domain.report.dto.ReportCreateRequest;
import com.example.demo.domain.report.dto.ReportCreateResponse;
import com.example.demo.domain.report.dto.ReportResolveRequest;
import com.example.demo.domain.report.dto.ReportResolveResponse;
import com.example.demo.domain.report.service.ReportService;
import com.example.demo.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Report", description = "신고 API")
@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "게시글 신고", description = "특정 게시글을 신고합니다. 동일 사용자의 중복 신고는 제한됩니다.")
    @PostMapping("/api/posts/{postId}/reports")
    public ResponseEntity<ApiResponse<ReportCreateResponse>> reportPost(
            @Parameter(description = "신고할 게시글 ID", example = "1")
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

    @Operation(summary = "댓글 신고", description = "특정 댓글을 신고합니다. 동일 사용자의 중복 신고는 제한됩니다.")
    @PostMapping("/api/comments/{commentId}/reports")
    public ResponseEntity<ApiResponse<ReportCreateResponse>> reportComment(
            @Parameter(description = "신고할 댓글 ID", example = "1")
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

    @Operation(summary = "신고 처리", description = "신고를 처리 완료 상태로 변경하고, 처리 방식에 따라 게시글 또는 댓글 상태를 변경합니다.")
    @PatchMapping("/api/reports/{reportId}/resolve")
    public ResponseEntity<ApiResponse<ReportResolveResponse>> resolveReport(
            @Parameter(description = "처리할 신고 ID", example = "1")
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
