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
}
