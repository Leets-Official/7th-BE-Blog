package com.example.leets_7th.domain.comment.controller;

import com.example.leets_7th.common.response.ApiResponse;
import com.example.leets_7th.common.status.SuccessStatus;
import com.example.leets_7th.domain.comment.controller.docs.CommentControllerDocs;
import com.example.leets_7th.domain.comment.dto.request.CreateCommentRequest;
import com.example.leets_7th.domain.comment.dto.request.ReportCommentRequest;
import com.example.leets_7th.domain.comment.dto.request.UpdateCommentRequest;
import com.example.leets_7th.domain.comment.service.CommentCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/posts")
@RestController
@Validated
@RequiredArgsConstructor
public class CommentController implements CommentControllerDocs {

    private final CommentCommandService commentCommandService;

    @Override
    @PostMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse<Void>> createComment(
            @RequestParam Long userId,
            @PathVariable Long postId,
            @RequestBody @Valid CreateCommentRequest request
    ) {
        commentCommandService.createComment(userId, postId, request);
        return ApiResponse.success(SuccessStatus.CREATE_COMMENT_SUCCESS);
    }

    @Override
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @RequestParam Long userId,
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        commentCommandService.deleteComment(userId, postId, commentId);
        return ApiResponse.success(SuccessStatus.DELETE_COMMENT_SUCCESS);
    }

    @Override
    @PatchMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> updateComment(
            @RequestParam Long userId,
            @PathVariable Long postId, Long commentId,
            @RequestBody @Valid UpdateCommentRequest request
    ) {
        commentCommandService.updateComment(userId, postId, commentId, request);
        return ApiResponse.success(SuccessStatus.UPDATE_COMMENT_SUCCESS);
    }

    @Override
    @PostMapping("/{postId}/comments/{commentId}/likes")
    public ResponseEntity<ApiResponse<Void>> likeComment(
            @RequestParam Long userId,
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        commentCommandService.likeComment(userId, postId, commentId);
        return ApiResponse.success(SuccessStatus.LIKE_COMMENT_SUCCESS);
    }

    @Override
    @DeleteMapping("/{postId}/comments/{commentId}/likes")
    public ResponseEntity<ApiResponse<Void>> unlikeComment(
            @RequestParam Long userId,
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        commentCommandService.unlikeComment(userId, postId, commentId);
        return ApiResponse.success(SuccessStatus.UNLIKE_COMMENT_CANCEL_SUCCESS);
    }

    @Override
    @PostMapping("/{postId}/comments/{commentId}/reports")
    public ResponseEntity<ApiResponse<Void>> reportComment(
            @RequestParam Long userId,
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody @Valid ReportCommentRequest request
    ) {
        commentCommandService.reportComment(userId, postId, commentId, request);
        return ApiResponse.success(SuccessStatus.REPORT_COMMENT_SUCCESS);
    }
}
