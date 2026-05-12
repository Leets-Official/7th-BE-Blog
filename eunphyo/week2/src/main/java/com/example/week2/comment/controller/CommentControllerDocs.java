package com.example.week2.comment.controller;

import com.example.week2.comment.dto.CommentCreateRequest;
import com.example.week2.comment.dto.CommentLikeRequest;
import com.example.week2.comment.dto.CommentResponse;
import com.example.week2.global.response.ApiResponse;
import com.example.week2.global.response.ErrorCode;
import com.example.week2.global.swagger.ApiErrorCodeExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comment", description = "댓글 API")
public interface CommentControllerDocs {

        @Operation(summary = "댓글 생성", description = "특정 댓글을 생성합니다.")
        @ApiErrorCodeExample({
                ErrorCode.POST_NOT_FOUND,
                ErrorCode.USER_NOT_FOUND
        })
        @PostMapping
        ResponseEntity<ApiResponse<CommentResponse.CreateCommentResponse>> createComment(
                @Parameter(description = "게시글 post ID", example = "1")
                @RequestParam Long postId,
                @Parameter(description = "작성자 user ID", example = "1")
                @RequestParam Long userId,
                @Valid @RequestBody CommentCreateRequest request
        );

        @Operation(summary = "댓글 조회", description = "특정 댓글을 조회합니다.")
        @ApiErrorCodeExample({ErrorCode.COMMENT_NOT_FOUND})
        @GetMapping("/{commentId}")
        ResponseEntity<ApiResponse<CommentResponse.CommentDetailResponse>> getComment(
                @Parameter(description = "게시글 post ID", example = "1")
                @PathVariable Long postId,
                @Parameter(description = "댓글 comment ID", example = "1")
                @PathVariable Long commentId
        );

        @Operation(summary = "댓글 좋아요", description = "특정 댓글에 좋아요를 표시합니다.")
        @ApiErrorCodeExample({
                ErrorCode.USER_NOT_FOUND,
                ErrorCode.COMMENT_ALREADY_LIKED
        })
        @PostMapping("/{commentId}/likes")
        ResponseEntity<ApiResponse<CommentResponse.CommentLikeResponse>> likeComment(
                @Parameter(description = "게시물 post ID", example = "1")
                @PathVariable Long postId,
                @Parameter(description = "댓글 comment ID", example = "1")
                @PathVariable Long commentId,
                @Valid @RequestBody CommentLikeRequest request
        );
    }
