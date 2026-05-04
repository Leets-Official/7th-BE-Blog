package com.leets.blog.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReportRequest {

    @Getter
    @NoArgsConstructor
    @Schema(name = "ReportCreateRequest")
    public static class Create {
        @NotBlank(message = "신고 사유는 필수입니다.")
        private String reason;
    }
}
