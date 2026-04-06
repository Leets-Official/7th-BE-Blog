package com.example.demo.comment.controller;

import com.example.demo.comment.dto.CommentCreateRequest;
import com.example.demo.comment.dto.CommentResponse;
import com.example.demo.comment.dto.CommentUpdateRequest;
import com.example.demo.comment.service.CommentService;
import com.example.demo.global.exception.ApiResponse;
import com.example.demo.global.exception.BaseCode;
import com.example.demo.global.exception.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    //댓글 생성
    @PostMapping
    public ApiResponse<Map<String, Long>> create(
            @RequestBody @Valid CommentCreateRequest request) {

        Long commentId = commentService.createComment(request);

        return ResponseUtil.success(
                BaseCode.COMMENT_CREATE_SUCCESS,
                Map.of("commentId", commentId)
        );
    }

    //특정 게시글 댓글 조회
    @GetMapping("/post/{postId}")
    public ApiResponse<List<CommentResponse>> getComments(
            @PathVariable Long postId) {

        return ResponseUtil.success(
                BaseCode.SUCCESS,
                commentService.getComments(postId)
        );
    }

    //댓글 수정
    @PutMapping("/{commentId}")
    public ApiResponse<Void> update(
            @PathVariable Long commentId,
            @RequestBody @Valid CommentUpdateRequest request) {

        commentService.updateComment(commentId, request);

        return ResponseUtil.success(
                BaseCode.COMMENT_UPDATE_SUCCESS,
                null
        );
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ApiResponse<Map<String, Long>> delete(
            @PathVariable Long commentId) {

        commentService.deleteComment(commentId);

        return ResponseUtil.success(
                BaseCode.COMMENT_DELETE_SUCCESS,
                Map.of("commentId", commentId)
        );
    }
}
