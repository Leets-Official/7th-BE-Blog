package com.leets.blog.domain.report.controller;

import com.leets.blog.common.response.ApiResponse;
import com.leets.blog.domain.report.dto.CommentReportResponse;
import com.leets.blog.domain.report.dto.CreateCommentReportRequest;
import com.leets.blog.domain.report.dto.CreatePostReportRequest;
import com.leets.blog.domain.report.dto.PostReportResponse;
import com.leets.blog.domain.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Tag(name = "Reports", description = "게시글/댓글 신고 API")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/posts/{postId}/reports")
    @Operation(summary = "게시글 신고", description = "특정 게시글을 신고합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 신고 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글 또는 사용자를 찾을 수 없음", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 신고를 완료함", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class)))
    })
    public ApiResponse<PostReportResponse> createPostReport(
            @PathVariable Long postId,
            @RequestBody @Valid CreatePostReportRequest request
    ) {
        return ApiResponse.onSuccess(reportService.createPostReport(postId, request));
    }

    @PostMapping("/comments/{commentId}/reports")
    @Operation(summary = "댓글 신고", description = "특정 댓글을 신고합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "댓글 신고 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "댓글 또는 사용자를 찾을 수 없음", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 신고를 완료함", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class)))
    })
    public ApiResponse<CommentReportResponse> createCommentReport(
            @PathVariable Long commentId,
            @RequestBody @Valid CreateCommentReportRequest request
    ) {
        return ApiResponse.onSuccess(reportService.createCommentReport(commentId, request));
    }

    @PatchMapping("/post-reports/{reportId}/resolve")
    @Operation(summary = "게시글 신고 처리", description = "게시글 신고를 처리 완료 상태로 변경합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 신고 처리 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "신고를 찾을 수 없음", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 처리 완료된 신고", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class)))
    })
    public ApiResponse<PostReportResponse> resolvePostReport(@PathVariable Long reportId) {
        return ApiResponse.onSuccess(reportService.resolvePostReport(reportId));
    }

    @PatchMapping("/comment-reports/{reportId}/resolve")
    @Operation(summary = "댓글 신고 처리", description = "댓글 신고를 처리 완료 상태로 변경합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "댓글 신고 처리 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "신고를 찾을 수 없음", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 처리 완료된 신고", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class)))
    })
    public ApiResponse<CommentReportResponse> resolveCommentReport(@PathVariable Long reportId) {
        return ApiResponse.onSuccess(reportService.resolveCommentReport(reportId));
    }
}
