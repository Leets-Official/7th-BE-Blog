package com.example.blog.domain.report.controller;

import com.example.blog.domain.report.dto.ReportResponse;
import com.example.blog.domain.report.service.ReportService;
import com.example.blog.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/{reportId}/resolve")
    public ApiResponse<ReportResponse> resolve(
        @RequestHeader("X-User-Id") Long handlerId,
        @PathVariable Long reportId
    ) {
        return ApiResponse.success(reportService.resolveReport(handlerId, reportId));
    }

    @GetMapping
    public ApiResponse<List<ReportResponse>> findAll() {
        return ApiResponse.success(reportService.findAll());
    }
}
