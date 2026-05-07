package com.example.demo.controller;

import com.example.demo.domain.comment.dto.CommentAdoptRequest;
import com.example.demo.domain.comment.dto.CommentCreateRequest;
import com.example.demo.domain.comment.dto.CommentCreateResponse;
import com.example.demo.domain.comment.dto.CommentStatusResponse;
import com.example.demo.domain.comment.service.CommentService;
import com.example.demo.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<CommentCreateResponse>> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "COMMENT_CREATE_SUCCESS",
                        "댓글 생성 성공",
                        commentService.createComment(postId, request)
                ));
    }

    @PostMapping("/api/comments/{commentId}/adoption")
    public ResponseEntity<ApiResponse<CommentStatusResponse>> adoptComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentAdoptRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "COMMENT_ADOPT_SUCCESS",
                        "댓글 채택 성공",
                        commentService.adoptComment(commentId, request.userId())
                )
        );
    }
}
