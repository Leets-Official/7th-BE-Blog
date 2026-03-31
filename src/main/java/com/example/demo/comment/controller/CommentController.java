package com.example.demo.comment.controller;

import com.example.demo.comment.dto.*;
import com.example.demo.comment.service.CommentService;
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
    public Long create(@RequestBody CommentCreateRequest request) {
        return commentService.createComment(request);
    }

    // 특정 게시글 댓글 조회
    @GetMapping("/post/{postId}")
    public List<CommentResponse> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public void update(@PathVariable Long commentId,
                       @RequestBody CommentUpdateRequest request) {
        commentService.updateComment(commentId, request);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
