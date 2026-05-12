package com.example.leets7th.domain.comment.controller;

import com.example.leets7th.domain.comment.dto.req.CommentRequestDTO;
import com.example.leets7th.domain.comment.dto.res.CommentResponseDTO;
import com.example.leets7th.domain.comment.exception.code.CommentSuccessCode;
import com.example.leets7th.domain.comment.service.CommentService;
import com.example.leets7th.global.apiPayload.ApiResponse;
import com.example.leets7th.global.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class CommentController implements CommentControllerDocs {

    private final CommentService commentService;

    @Override
    @PatchMapping("/api/posts/{postId}/comments/{commentId}/adopt")
    public ApiResponse<Void> adoptComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        commentService.adoptComment(SecurityUtil.getCurrentUserId(), postId, commentId);
        return ApiResponse.onSuccess(CommentSuccessCode.ADOPT_COMMENT_SUCCESS, null);
    }

    @Override
    @GetMapping("/api/posts/{postId}/comments")
    public ApiResponse<List<CommentResponseDTO.CommentResDTO>> getCommentList(@PathVariable Long postId) {
        List<CommentResponseDTO.CommentResDTO> result = commentService.getCommentList(SecurityUtil.getCurrentUserId(), postId);
        return ApiResponse.onSuccess(CommentSuccessCode.GET_COMMENT_LIST_SUCCESS, result);
    }

    @Override
    @PostMapping("/api/posts/{postId}/comments")
    public ApiResponse<CommentResponseDTO.CreateCommentResDTO> createComment(
            @PathVariable Long postId,
            @RequestBody @Valid CommentRequestDTO.CreateCommentDTO request
    ) {
        CommentResponseDTO.CreateCommentResDTO result = commentService.createComment(SecurityUtil.getCurrentUserId(), postId, request);
        return ApiResponse.onSuccess(CommentSuccessCode.CREATE_COMMENT_SUCCESS, result);
    }

    @Override
    @PostMapping("/api/posts/{postId}/comments/{commentId}/replies")
    public ApiResponse<CommentResponseDTO.CreateCommentResDTO> createReply(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody @Valid CommentRequestDTO.CreateCommentDTO request
    ) {
        CommentResponseDTO.CreateCommentResDTO result = commentService.createReply(SecurityUtil.getCurrentUserId(), postId, commentId, request);
        return ApiResponse.onSuccess(CommentSuccessCode.CREATE_REPLY_SUCCESS, result);
    }
}
