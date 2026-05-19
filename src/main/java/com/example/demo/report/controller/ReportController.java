package com.example.demo.report.controller;

import com.example.demo.global.exception.ApiResponse;
import com.example.demo.global.exception.BaseCode;
import com.example.demo.global.exception.ErrorResponse;
import com.example.demo.global.exception.ResponseUtil;
import com.example.demo.report.dto.ReportCreateRequest;
import com.example.demo.report.dto.ReportResponse;
import com.example.demo.report.service.ReportService;
import com.example.demo.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Report", description = "신고 관련 API")
@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @Operation(
            summary = "게시글 신고 API",
            description = "로그인한 사용자가 postId에 해당하는 게시글을 신고합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "게시글 신고 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 신고 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증되지 않은 사용자",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "게시글을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/posts/{postId}/reports")
    public ResponseEntity<ApiResponse<ReportResponse>> reportPost(
            @AuthenticationPrincipal User user,

            @Parameter(description = "신고할 게시글 ID", example = "1")
            @PathVariable Long postId,

            @RequestBody ReportCreateRequest request
    ) {
        ReportResponse response = reportService.reportPost(
                user.getId(),
                postId,
                request
        );

        return ResponseEntity.ok(
                ResponseUtil.success(BaseCode.REPORT_POST_SUCCESS, response)
        );
    }

    @Operation(
            summary = "댓글 신고 API",
            description = "로그인한 사용자가 commentId에 해당하는 댓글을 신고합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "댓글 신고 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 신고 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증되지 않은 사용자",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "댓글을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/comments/{commentId}/reports")
    public ResponseEntity<ApiResponse<ReportResponse>> reportComment(
            @AuthenticationPrincipal User user,

            @Parameter(description = "신고할 댓글 ID", example = "1")
            @PathVariable Long commentId,

            @RequestBody ReportCreateRequest request
    ) {
        ReportResponse response = reportService.reportComment(
                user.getId(),
                commentId,
                request
        );

        return ResponseEntity.ok(
                ResponseUtil.success(BaseCode.REPORT_COMMENT_SUCCESS, response)
        );
    }

    @Operation(
            summary = "신고 처리 완료 API",
            description = "reportId에 해당하는 신고 상태를 처리 완료로 변경합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "신고 처리 완료 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증되지 않은 사용자",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "신고를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping("/reports/{reportId}/resolve")
    public ResponseEntity<ApiResponse<ReportResponse>> resolveReport(
            @AuthenticationPrincipal User user,

            @Parameter(description = "처리할 신고 ID", example = "1")
            @PathVariable Long reportId
    ) {
        ReportResponse response = reportService.resolveReport(
                user.getId(),
                reportId
        );

        return ResponseEntity.ok(
                ResponseUtil.success(BaseCode.REPORT_RESOLVE_SUCCESS, response)
        );
    }
}