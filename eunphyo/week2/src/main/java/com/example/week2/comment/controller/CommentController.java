package com.example.week2.comment.controller;

import com.example.week2.comment.dto.CommentCreateRequest;
import com.example.week2.comment.dto.CommentLikeRequest;
import com.example.week2.comment.dto.CommentResponse;
import com.example.week2.comment.service.CommentService;
import com.example.week2.global.response.ApiResponse;
import com.example.week2.global.response.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController implements CommentControllerDocs{

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponse.CreateCommentResponse>> createComment(

            @PathVariable Long postId,
            @RequestParam Long userId,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        CommentResponse.CreateCommentResponse response =
            commentService.createComment(postId, userId, request);

        return ResponseEntity
                .status(SuccessCode.COMMENT_CREATED.getStatus())
                .body(ApiResponse.success(SuccessCode.COMMENT_CREATED, response));

    }


    @GetMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse.CommentDetailResponse>> getComment(

            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        CommentResponse.CommentDetailResponse response =
                commentService.getCommentResponse(commentId);

        return ResponseEntity
                .status(SuccessCode.COMMENT_GET.getStatus())
                .body(ApiResponse.success(SuccessCode.COMMENT_GET, response));
    }


    @PostMapping("/{commentId}/likes")
    public ResponseEntity<ApiResponse<CommentResponse.CommentLikeResponse>> likeComment(

            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentLikeRequest request
    ) {
        CommentResponse.CommentLikeResponse response =
            commentService.likeComment(
                commentId,
                request.getUserId()
        );

        return ResponseEntity
                .status(SuccessCode.COMMENT_LIKED.getStatus())
                .body(ApiResponse.success(SuccessCode.COMMENT_LIKED, response));
    }
} //