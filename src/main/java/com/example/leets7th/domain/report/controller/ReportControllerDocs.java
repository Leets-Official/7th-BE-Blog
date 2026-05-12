package com.example.leets7th.domain.report.controller;

import com.example.leets7th.domain.report.dto.req.ReportReqDTO;
import com.example.leets7th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Report", description = "신고 관련 API")
public interface ReportControllerDocs {

    @Operation(summary = "게시글 신고 api", description = "게시글을 신고합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REPORT200_5", description = "게시글 신고에 성공했습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH403_1", description = "인증이 필요합니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST404_1", description = "해당 게시글이 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REPORT400_2", description = "자신의 게시글은 신고할 수 없습니다.")
    })
    @PostMapping("/api/report/{postId}")
    ApiResponse<Void> reportPost(@RequestBody @Valid ReportReqDTO.CreateReportDTO request);

    @Operation(summary = "신고 처리 api (admin)", description = "신고된 게시글을 처리합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REPORT200_6", description = "신고 처리에 성공했습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST404_1", description = "해당 게시글이 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REPORT404_1", description = "해당 게시글에 대한 신고가 존재하지 않습니다.")
    })
    @PatchMapping("/api/admin/report/{postId}")
    ApiResponse<Void> processReport(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId
    );
}
