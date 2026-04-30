package com.example.leets_exercise1.dto.report.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostReportCreateRequest {

    @NotNull(message = "reporterId는 필수입니다.")
    private Long reporterId;

    @NotBlank(message = "신고 제목은 비어 있을 수 없습니다.")
    @Size(max = 255, message = "신고 제목은 255자 이하여야 합니다.")
    private String title;
}