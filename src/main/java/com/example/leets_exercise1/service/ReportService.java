package com.example.leets_exercise1.service;

import com.example.leets_exercise1.dto.report.request.CommentReportCreateRequest;
import com.example.leets_exercise1.dto.report.request.PostReportCreateRequest;
import com.example.leets_exercise1.dto.report.response.ReportCreateResponse;
import com.example.leets_exercise1.dto.report.response.ReportResolveResponse;

public interface ReportService {

    ReportCreateResponse reportPost(Long postId, PostReportCreateRequest request);

    ReportCreateResponse reportComment(Long commentId, CommentReportCreateRequest request);

    ReportResolveResponse resolveReport(Long reportId);
}