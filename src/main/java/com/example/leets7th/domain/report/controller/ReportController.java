package com.example.leets7th.domain.report.controller;

import com.example.leets7th.domain.report.dto.req.ReportReqDTO;
import com.example.leets7th.domain.report.exception.code.ReportSuccessCode;
import com.example.leets7th.domain.report.service.ReportService;
import com.example.leets7th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
public class ReportController implements ReportControllerDocs{
    private final ReportService reportService;

    // 게시글 신고 API
    @Override
    @PostMapping("/api/report/{postId}")
    public ApiResponse<Void> reportPost(
            @RequestHeader @Valid Long userId,
            @RequestBody @Valid ReportReqDTO.CreateReportDTO request
    ) {
        reportService.reportPost(request, userId);
        return ApiResponse.onSuccess(ReportSuccessCode.Report_POST_SUCCESS, null);
    }

    // 신고 처리 API (admin)
    @Override
    @PatchMapping("/api/admin/report/{postId}")
    public ApiResponse<Void> processReport(@PathVariable Long postId) {
        reportService.processReport(postId);
        return ApiResponse.onSuccess(ReportSuccessCode.PROCESS_REPORT_SUCCESS, null);
    }
}
