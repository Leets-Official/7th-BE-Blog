package com.example.blog.domain.report.dto;

import jakarta.validation.constraints.NotBlank;

public record ReportPostRequest(
    @NotBlank(message = "신고 사유는 필수입니다.")
    String reason
) {}
