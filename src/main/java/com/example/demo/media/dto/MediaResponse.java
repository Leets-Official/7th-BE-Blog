package com.example.demo.media.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "미디어 업로드 응답 DTO")
public class MediaResponse {

    @Schema(description = "미디어 ID", example = "1")
    private Long id;

    @Schema(description = "저장된 파일 접근 URL", example = "http://localhost:8080/images/example.jpg")
    private String url;
}
