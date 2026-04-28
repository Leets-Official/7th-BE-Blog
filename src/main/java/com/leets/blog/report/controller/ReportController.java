package com.leets.blog.report.controller;

import com.leets.blog.global.common.BaseResponse;
import com.leets.blog.report.dto.ReportRequest;
import com.leets.blog.report.dto.ReportResponse;
import com.leets.blog.report.service.ReportService;
import com.leets.blog.user.auth.AuthUser;
import com.leets.blog.user.auth.CurrentUser;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/comments/{commentId}/reports")
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
    public ResponseEntity<BaseResponse<ReportResponse>> resolve(
            @Parameter(hidden = true)
            @CurrentUser AuthUser authUser,
            @PathVariable Long reportId
    ) {
        ReportResponse response = reportService.resolve(authUser, reportId);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }
}
