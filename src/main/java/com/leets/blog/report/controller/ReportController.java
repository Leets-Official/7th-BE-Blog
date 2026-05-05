package com.leets.blog.report.controller;

import com.leets.blog.global.common.BaseResponse;
import com.leets.blog.report.dto.ReportRequest;
import com.leets.blog.report.dto.ReportResponse;
import com.leets.blog.report.service.ReportService;
import com.leets.blog.user.auth.AuthUser;
import com.leets.blog.user.auth.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "report-controller", description = "게시글/댓글 신고 및 처리(RESOLVE) API")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/comments/{commentId}/reports")
    @Operation(
            summary = "댓글 신고",
            description = "특정 댓글을 신고합니다. 임시 인증으로 헤더 `X-USER-ID` 값을 사용합니다."
    )
    public ResponseEntity<BaseResponse<ReportResponse>> reportComment(
            @Parameter(hidden = true)
            @CurrentUser AuthUser authUser,
            @PathVariable Long commentId,
            @Valid @RequestBody ReportRequest.Create request
    ) {
        ReportResponse response = reportService.reportComment(authUser, commentId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.ok(response));
    }

    @PostMapping("/posts/{postId}/reports")
    @Operation(
            summary = "게시글 신고",
            description = "특정 게시글을 신고합니다. 임시 인증으로 헤더 `X-USER-ID` 값을 사용합니다."
    )
    public ResponseEntity<BaseResponse<ReportResponse>> reportPost(
            @Parameter(hidden = true)
            @CurrentUser AuthUser authUser,
            @PathVariable Long postId,
            @Valid @RequestBody ReportRequest.Create request
    ) {
        ReportResponse response = reportService.reportPost(authUser, postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.ok(response));
    }

    @PatchMapping("/reports/{reportId}/resolve")
    @Operation(
            summary = "신고 처리(RESOLVE)",
            description = "신고를 처리 상태로 변경합니다. (예: 관리자만 가능)"
    )
    public ResponseEntity<BaseResponse<ReportResponse>> resolve(
            @Parameter(hidden = true)
            @CurrentUser AuthUser authUser,
            @PathVariable Long reportId
    ) {
        ReportResponse response = reportService.resolve(authUser, reportId);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }

    @GetMapping("/reports")
    @Operation(
            summary = "신고 목록 조회",
            description = "신고 목록을 조회합니다. (예: 관리자만 가능)"
    )
    public ResponseEntity<BaseResponse<List<ReportResponse>>> findAll(
            @Parameter(hidden = true)
            @CurrentUser AuthUser authUser
    ) {
        List<ReportResponse> response = reportService.findAll(authUser);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }

    @GetMapping("/reports/{reportId}")
    @Operation(
            summary = "신고 단건 조회",
            description = "신고 ID로 신고 정보를 조회합니다. (예: 관리자만 가능)"
    )
    public ResponseEntity<BaseResponse<ReportResponse>> findById(
            @Parameter(hidden = true)
            @CurrentUser AuthUser authUser,
            @PathVariable Long reportId
    ) {
        ReportResponse response = reportService.findById(authUser, reportId);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }
}
