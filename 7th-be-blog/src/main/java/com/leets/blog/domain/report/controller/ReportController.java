package com.leets.blog.domain.report.controller;

import com.leets.blog.common.response.ApiResponse;
import com.leets.blog.domain.report.dto.CommentReportResponse;
import com.leets.blog.domain.report.dto.CreateCommentReportRequest;
import com.leets.blog.domain.report.dto.CreatePostReportRequest;
import com.leets.blog.domain.report.dto.PostReportResponse;
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
@RequestMapping
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/posts/{postId}/reports")
    public ApiResponse<PostReportResponse> createPostReport(
            @PathVariable Long postId,
            @RequestBody @Valid CreatePostReportRequest request
    ) {
        return ApiResponse.onSuccess(reportService.createPostReport(postId, request));
    }

    @PostMapping("/comments/{commentId}/reports")
    public ApiResponse<CommentReportResponse> createCommentReport(
            @PathVariable Long commentId,
            @RequestBody @Valid CreateCommentReportRequest request
    ) {
        return ApiResponse.onSuccess(reportService.createCommentReport(commentId, request));
    }
}
