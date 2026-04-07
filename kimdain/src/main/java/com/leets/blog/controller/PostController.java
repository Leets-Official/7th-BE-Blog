package com.leets.blog.controller;

import com.leets.blog.dto.*;
import com.leets.blog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 게시글 작성
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody PostCreateRequest request) {
        Long id = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("id", id, "message", "게시글이 생성되었습니다"));
    }

    // 게시글 목록 조회
    @GetMapping
    public ResponseEntity<PostListResponse> getAll() {
        return ResponseEntity.ok(postService.getPosts());
    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostDetailResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    // 게시글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, String>> update(
            @PathVariable Long id,
            @Valid @RequestBody PostUpdateRequest request) {
        postService.updatePost(id, request);
        return ResponseEntity.ok(Map.of("message", "게시글이 수정되었습니다."));
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable Long id,
            @RequestParam Long userId) {
        postService.deletePost(id, userId);
        return ResponseEntity.ok(Map.of("message", "게시글이 삭제되었습니다."));
    }
}