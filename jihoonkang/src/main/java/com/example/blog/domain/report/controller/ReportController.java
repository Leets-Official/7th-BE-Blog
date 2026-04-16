package com.example.blog.domain.report.controller;

import com.example.blog.domain.report.dto.ReportCreateRequest;
import com.example.blog.domain.report.dto.ReportResponse;
import com.example.blog.domain.report.service.ReportService;
import com.example.blog.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReportResponse> create(
        @RequestHeader("X-User-Id") Long reporterId,
        @RequestBody @Valid ReportCreateRequest request
    ) {
        return ApiResponse.success(reportService.create(reporterId, request));
    }

    @GetMapping
    public ApiResponse<List<ReportResponse>> findAll() {
        return ApiResponse.success(reportService.findAll());
    }
}
