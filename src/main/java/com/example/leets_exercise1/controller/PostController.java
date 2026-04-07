package com.example.leets_exercise1.controller;

import com.example.leets_exercise1.common.response.ApiResponse;
import com.example.leets_exercise1.dto.post.request.PostCreateRequest;
import com.example.leets_exercise1.dto.post.request.PostUpdateRequest;
import com.example.leets_exercise1.dto.post.response.*;
import com.example.leets_exercise1.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Validated
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<ApiResponse<PostListResult>> getPosts(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "page는 1 이상이어야 합니다.") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "size는 1 이상이어야 합니다.") int size
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("2000", "게시글 목록 조회 성공", postService.getPosts(page, size))
        );
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(
                ApiResponse.success("2001", "게시글 조회 성공", postService.getPost(postId))
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PostCreateResponse>> createPost(
            @Valid @RequestBody PostCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("2010", "게시글 생성 성공", postService.createPost(request)));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("2002", "게시글 수정 성공", postService.updatePost(postId, request))
        );
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDeleteResponse>> deletePost(@PathVariable Long postId) {
        return ResponseEntity.ok(
                ApiResponse.success("2003", "게시글 삭제 성공", postService.deletePost(postId))
        );
    }
}