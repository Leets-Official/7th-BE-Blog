package com.example.demo.controller;

import com.example.demo.domain.comment.dto.CommentAdoptRequest;
import com.example.demo.domain.comment.dto.CommentCreateRequest;
import com.example.demo.domain.comment.dto.CommentCreateResponse;
import com.example.demo.domain.comment.dto.CommentStatusResponse;
import com.example.demo.domain.comment.service.CommentService;
import com.example.demo.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comment", description = "댓글 API")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 생성", description = "특정 게시글에 댓글을 작성합니다.")
    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<CommentCreateResponse>> createComment(
            @Parameter(description = "댓글을 작성할 게시글 ID", example = "1")
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

    @Operation(summary = "댓글 채택", description = "게시글 작성자가 댓글 하나를 채택합니다. 이미 채택된 댓글이 있으면 실패합니다.")
    @PostMapping("/api/comments/{commentId}/adoption")
    public ResponseEntity<ApiResponse<CommentStatusResponse>> adoptComment(
            @Parameter(description = "채택할 댓글 ID", example = "1")
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
