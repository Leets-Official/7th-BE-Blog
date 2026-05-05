package com.leets.blog.comment.controller;

import com.leets.blog.comment.dto.CommentRequest;
import com.leets.blog.comment.dto.CommentResponse;
import com.leets.blog.comment.service.CommentService;
import com.leets.blog.global.common.BaseResponse;
import com.leets.blog.user.auth.AuthUser;
import com.leets.blog.user.auth.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "comment-controller", description = "댓글 생성/조회 및 채택 처리 API")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    @Operation(
            summary = "댓글 생성",
            description = "특정 게시글에 댓글을 생성합니다. 임시 인증으로 헤더 `X-USER-ID` 값을 사용합니다."
    )
    public ResponseEntity<BaseResponse<CommentResponse>> create(
            @Parameter(hidden = true)
            @CurrentUser AuthUser authUser,
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest.Create request
    ) {
        CommentResponse response = commentService.create(authUser, postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.ok(response));
    }

    @PatchMapping("/comments/{commentId}/accept")
    @Operation(
            summary = "댓글 채택",
            description = "댓글을 채택 처리합니다. (예: 게시글 작성자만 가능)"
    )
    public ResponseEntity<BaseResponse<CommentResponse>> accept(
            @Parameter(hidden = true)
            @CurrentUser AuthUser authUser,
            @PathVariable Long commentId
    ) {
        CommentResponse response = commentService.accept(authUser, commentId);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }

    @GetMapping("/posts/{postId}/comments")
    @Operation(
            summary = "게시글의 댓글 목록 조회",
            description = "특정 게시글에 달린 댓글 목록을 조회합니다."
    )
    public ResponseEntity<BaseResponse<List<CommentResponse>>> findByPostId(@PathVariable Long postId) {
        List<CommentResponse> response = commentService.findByPostId(postId);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }

    @GetMapping("/comments/{commentId}")
    @Operation(
            summary = "댓글 단건 조회",
            description = "댓글 ID로 댓글을 조회합니다."
    )
    public ResponseEntity<BaseResponse<CommentResponse>> findById(@PathVariable Long commentId) {
        CommentResponse response = commentService.findById(commentId);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }
}
