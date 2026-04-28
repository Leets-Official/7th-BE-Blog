package com.example.blog.domain.comment.controller;

import com.example.blog.domain.comment.dto.CommentCreateRequest;
import com.example.blog.domain.comment.dto.CommentResponse;
import com.example.blog.domain.comment.dto.CommentUpdateRequest;
import com.example.blog.domain.comment.service.CommentService;
import com.example.blog.domain.report.dto.ReportCommentRequest;
import com.example.blog.domain.report.dto.ReportResponse;
import com.example.blog.domain.report.service.ReportService;
import com.example.blog.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ReportService reportService;

    @PostMapping("/api/v1/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CommentResponse> create(
        @RequestHeader("X-User-Id") Long userId,
        @PathVariable Long postId,
        @RequestBody @Valid CommentCreateRequest request
    ) {
        return ApiResponse.success(commentService.create(userId, postId, request));
    }

    @GetMapping("/api/v1/posts/{postId}/comments")
    public ApiResponse<List<CommentResponse>> findByPostId(@PathVariable Long postId) {
        return ApiResponse.success(commentService.findByPostId(postId));
    }

    @PatchMapping("/api/v1/comments/{commentId}")
    public ApiResponse<CommentResponse> update(
        @RequestHeader("X-User-Id") Long userId,
        @PathVariable Long commentId,
        @RequestBody @Valid CommentUpdateRequest request
    ) {
        return ApiResponse.success(commentService.update(userId, commentId, request));
    }

    @DeleteMapping("/api/v1/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
        @RequestHeader("X-User-Id") Long userId,
        @PathVariable Long commentId
    ) {
        commentService.delete(userId, commentId);
    }

    @PostMapping("/api/v1/posts/{postId}/comments/{commentId}/accept")
    public ApiResponse<CommentResponse> accept(
        @RequestHeader("X-User-Id") Long userId,
        @PathVariable Long postId,
        @PathVariable Long commentId
    ) {
        return ApiResponse.success(commentService.acceptComment(userId, postId, commentId));
    }

    @PostMapping("/api/v1/comments/{commentId}/reports")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReportResponse> reportComment(
        @RequestHeader("X-User-Id") Long reporterId,
        @PathVariable Long commentId,
        @RequestBody @Valid ReportCommentRequest request
    ) {
        return ApiResponse.success(reportService.reportComment(reporterId, commentId, request));
    }
}
