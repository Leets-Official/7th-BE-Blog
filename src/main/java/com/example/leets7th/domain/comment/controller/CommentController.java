package com.example.leets7th.domain.comment.controller;

import com.example.leets7th.domain.comment.dto.CommentCreateRequest;
import com.example.leets7th.domain.comment.service.CommentService;
import com.example.leets7th.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController implements CommentControllerDocs {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> createComment(
            @PathVariable Long postId,
            @RequestBody @Valid CommentCreateRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId
    ) {
        Long commentId = commentService.createComment(postId, request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(Map.of("commentId", commentId, "message", "댓글이 작성되었습니다.")));
    }

    @PatchMapping("/{commentId}/adopt")
    public ResponseEntity<ApiResponse<Map<String, String>>> adoptComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId
    ) {
        commentService.adoptComment(postId, commentId, userId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("message", "댓글이 채택되었습니다.")));
    }
}
