package com.example.leets7th.domain.comment.controller;

import com.example.leets7th.domain.comment.dto.req.CommentRequestDTO;
import com.example.leets7th.domain.comment.dto.res.CommentResponseDTO;
import com.example.leets7th.domain.comment.exception.code.CommentSuccessCode;
import com.example.leets7th.domain.comment.service.CommentService;
import com.example.leets7th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
public class CommentController implements CommentControllerDocs {
    private final CommentService commentService;

    // 댓글 작성 API
    @Override
    @PostMapping("/api/posts/{postId}/comments")
    public ApiResponse<CommentResponseDTO.CreateCommentResDTO> createComment(
            @RequestHeader @Valid Long userId,
            @PathVariable Long postId,
            @RequestBody @Valid CommentRequestDTO.CreateCommentDTO request
    ) {
        CommentResponseDTO.CreateCommentResDTO result = commentService.createComment(userId, postId, request);
        return ApiResponse.onSuccess(CommentSuccessCode.CREATE_COMMENT_SUCCESS, result);
    }

    // 대댓글 작성 API
    @Override
    @PostMapping("/api/posts/{postId}/comments/{commentId}/replies")
    public ApiResponse<CommentResponseDTO.CreateCommentResDTO> createReply(
            @RequestHeader @Valid Long userId,
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody @Valid CommentRequestDTO.CreateCommentDTO request
    ) {
        CommentResponseDTO.CreateCommentResDTO result = commentService.createReply(userId, postId, commentId, request);
        return ApiResponse.onSuccess(CommentSuccessCode.CREATE_REPLY_SUCCESS, result);
    }
}
