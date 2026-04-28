package com.leets.blog.comment.controller;

import com.leets.blog.comment.dto.CommentRequest;
import com.leets.blog.comment.dto.CommentResponse;
import com.leets.blog.comment.service.CommentService;
import com.leets.blog.global.common.BaseResponse;
import com.leets.blog.user.auth.AuthUser;
import com.leets.blog.user.auth.CurrentUser;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
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
    public ResponseEntity<BaseResponse<CommentResponse>> accept(
            @Parameter(hidden = true)
            @CurrentUser AuthUser authUser,
            @PathVariable Long commentId
    ) {
        CommentResponse response = commentService.accept(authUser, commentId);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<BaseResponse<List<CommentResponse>>> findByPostId(@PathVariable Long postId) {
        List<CommentResponse> response = commentService.findByPostId(postId);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<BaseResponse<CommentResponse>> findById(@PathVariable Long commentId) {
        CommentResponse response = commentService.findById(commentId);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }
}
