package com.example.week2.comment.controller;

import com.example.week2.comment.dto.CommentCreateRequest;
import com.example.week2.comment.dto.CommentLikeRequest;
import com.example.week2.comment.dto.CommentReportRequest;
import com.example.week2.comment.dto.CommentResponse;
import com.example.week2.comment.service.CommentLikeService;
import com.example.week2.comment.service.CommentReportService;
import com.example.week2.comment.service.CommentService;
import com.example.week2.global.exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final CommentLikeService commentLikeService;
    private final CommentReportService commentReportService;


    @PostMapping
    public ApiResponse<CommentResponse.CreateCommentResponse> createComment(
            @PathVariable Long postId,
            @RequestParam Long userId,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        CommentResponse.CreateCommentResponse response =
            commentService.createComment(postId, userId, request);

        return ApiResponse.success("COMMENT_CREATED", "댓글 작성 성공", response);

    }

    @GetMapping("/{commentId}")
    public ApiResponse<CommentResponse.CommentDetailResponse> getComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        CommentResponse.CommentDetailResponse response =
                commentService.getCommentResponse(commentId);

        return ApiResponse.success("COMMENT_FOUND", "댓글 조회 성공", response);
    }

    @PostMapping("/{commentId}/likes")
    public ApiResponse<Void> likeComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentLikeRequest request
    ) {

        commentLikeService.likeComment(
                commentId,
                request.getUserId()
        );

        return ApiResponse.success(
                "COMMENT_LIKED",
                "댓글 좋아요 성공",
                null
        );
    }

    @PostMapping("/{commentId}/reports")
    public ApiResponse<CommentResponse.CommentReportResponse> reportComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentReportRequest request
    ) {
        CommentResponse.CommentReportResponse response =
                commentReportService.reportComment(commentId, request);

        return ApiResponse.success(
                "COMMENT_REPORTED",
                "댓글 신고 성공",
                response
        );
    }

    @PatchMapping("/{commentId}/reports/{reportId}/resolve")
    public ApiResponse<CommentResponse.CommentReportResolve> resolveReport(
            @PathVariable Long commentId,
            @PathVariable Long reportId
    ) {
        CommentResponse.CommentReportResolve response =
                commentReportService.resolveReport(reportId);

        return ApiResponse.success(
                "COMMENT_REPORT_RESOLVED",
                "댓글 신고 처리 완료 (상태 변경: PENDING → RESOLVED)",
                response
        );
    }
}