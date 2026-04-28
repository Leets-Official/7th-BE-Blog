package com.example.leets7th.domain.comment.controller;

import com.example.leets7th.domain.comment.service.CommentService;
import com.example.leets7th.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController implements CommentControllerDocs {

    private final CommentService commentService;

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
