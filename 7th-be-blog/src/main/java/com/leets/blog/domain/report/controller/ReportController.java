package com.leets.blog.domain.report.controller;

import com.leets.blog.common.response.ApiResponse;
import com.leets.blog.domain.report.dto.CreatePostReportRequest;
import com.leets.blog.domain.report.dto.ReportResponse;
import com.leets.blog.domain.report.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/reports")
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ApiResponse<ReportResponse> createPostReport(
            @PathVariable Long postId,
            @RequestBody @Valid CreatePostReportRequest request
    ) {
        return ApiResponse.onSuccess(reportService.createPostReport(postId, request));
    }
}
