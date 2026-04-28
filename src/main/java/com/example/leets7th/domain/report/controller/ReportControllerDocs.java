package com.example.leets7th.domain.report.controller;

import com.example.leets7th.domain.report.dto.req.ReportReqDTO;
import com.example.leets7th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "Report", description = "신고 관련 API")
public interface ReportControllerDocs {
    @Operation(
            summary = "게시글 신고 api",
            description = "게시글을 신고합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST200_5", description = "게시글 신고에 성공했습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH401_1", description = "인증이 필요합니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST404_1", description = "해당 게시글이 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST403_1", description = "접근 권한이 없습니다."),

    })
    @PostMapping("/api/report/{postId}")
    ApiResponse<Void> reportPost(
            @RequestHeader @Valid Long userId,
            @RequestBody @Valid ReportReqDTO.CreateReportDTO request
    );
}
