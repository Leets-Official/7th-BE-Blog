package com.example.week2.report.controller;

import com.example.week2.global.response.ApiResponse;
import com.example.week2.global.response.SuccessCode;
import com.example.week2.report.dto.ReportRequest;
import com.example.week2.report.dto.ReportResponse;
import com.example.week2.report.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments/{commentId}")
public class ReportController {

    private final ReportService commentReportService;

    @PostMapping("/reports")
    public ApiResponse<ReportResponse.CommentReportResponse> reportComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody ReportRequest request
    ) {
        ReportResponse.CommentReportResponse response =
                commentReportService.reportComment(commentId, request);

        return ApiResponse.success(SuccessCode.REPORT_COMMENT_CREATED, response);
    }

    @PatchMapping("/reports/{reportId}/resolves")
    public ApiResponse<ReportResponse.CommentReportResolve> resolveReport(
            @PathVariable Long commentId,
            @PathVariable Long reportId
    ) {
        ReportResponse.CommentReportResolve response =
                commentReportService.resolveReport(reportId);

        return ApiResponse.success(SuccessCode.REPORT_COMMENT_RESOLVED, response);
    }
}
