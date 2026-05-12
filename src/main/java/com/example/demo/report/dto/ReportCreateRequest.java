package com.example.demo.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "신고 생성 요청 DTO")
public class ReportCreateRequest {

    @Schema(
            description = "신고 사유",
            example = "욕설 및 비방 내용이 포함되어 있습니다."
    )
    private String reason;
}