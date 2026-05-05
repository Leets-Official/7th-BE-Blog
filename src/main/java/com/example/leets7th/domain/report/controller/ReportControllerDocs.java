package com.example.leets7th.domain.report.controller;

import com.example.leets7th.domain.report.dto.ReportRequest;
import com.example.leets7th.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@Tag(name = "Report", description = "신고 관련 API")
public interface ReportControllerDocs {

    @Operation(summary = "게시글 신고", description = "게시글을 신고합니다. 동일한 게시글에 중복 신고는 불가합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "신고 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "중복 신고")
    })
    ResponseEntity<ApiResponse<Map<String, Object>>> reportPost(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId,
            @RequestBody @Valid ReportRequest request,
            @Parameter(description = "신고자 ID (임시)", example = "1")
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId
    );

    @Operation(summary = "댓글 신고", description = "댓글을 신고합니다. 동일한 댓글에 중복 신고는 불가합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "신고 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "댓글 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "중복 신고")
    })
    ResponseEntity<ApiResponse<Map<String, Object>>> reportComment(
            @Parameter(description = "댓글 ID", example = "1") @PathVariable Long commentId,
            @RequestBody @Valid ReportRequest request,
            @Parameter(description = "신고자 ID (임시)", example = "1")
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId
    );

    @Operation(summary = "신고 처리 완료", description = "신고 상태를 PENDING에서 RESOLVED로 변경합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "처리 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "신고 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 처리된 신고")
    })
    ResponseEntity<ApiResponse<Map<String, String>>> resolveReport(
            @Parameter(description = "신고 ID", example = "1") @PathVariable Long reportId
    );
}
