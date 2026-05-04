package com.example.week2.report.controller;


import com.example.week2.global.response.ApiResponse;
import com.example.week2.global.response.SuccessCode;
import com.example.week2.report.dto.ReportRequest;
import com.example.week2.report.dto.ReportResponse;
import com.example.week2.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments/{commentId}/reports")
public class ReportController implements ReportControllerDocs{

    private final ReportService commentReportService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReportResponse.CommentReportResponse>> reportComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody ReportRequest request
    ) {
        ReportResponse.CommentReportResponse response =
                commentReportService.reportComment(commentId, request);

        return ResponseEntity
                .status(SuccessCode.REPORT_COMMENT_CREATED.getStatus())
                .body(ApiResponse.success(SuccessCode.REPORT_COMMENT_CREATED, response));
    }


    @PatchMapping("/{reportId}/resolves")
    public ResponseEntity<ApiResponse<ReportResponse.CommentReportResolve>> resolveReport(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @PathVariable Long reportId
    ) {
        ReportResponse.CommentReportResolve response =
                commentReportService.resolveReport(reportId);

        return ResponseEntity
                .status(SuccessCode.REPORT_COMMENT_RESOLVED.getStatus())
                .body(ApiResponse.success(SuccessCode.REPORT_COMMENT_RESOLVED, response));
    }
}
