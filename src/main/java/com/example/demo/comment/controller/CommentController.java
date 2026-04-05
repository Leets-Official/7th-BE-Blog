package com.example.demo.comment.controller;

import com.example.demo.comment.dto.*;
import com.example.demo.comment.service.CommentService;
import com.example.demo.global.exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping
    public ApiResponse<Long> create(@RequestBody @Valid CommentCreateRequest request) {
        return ApiResponse.<Long>builder()
                .success(true)
                .data(commentService.createComment(request))
                .build();
    }

    // 특정 게시글 댓글 조회
    @GetMapping("/post/{postId}")
    public List<CommentResponse> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ApiResponse<Void> update(
            @PathVariable Long commentId,
            @RequestBody @Valid CommentUpdateRequest request) {

        commentService.updateComment(commentId, request);

        return ApiResponse.<Void>builder()
                .success(true)
                .build();
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> delete(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);

        return ApiResponse.<Void>builder()
                .success(true)
                .build();
    }
}
