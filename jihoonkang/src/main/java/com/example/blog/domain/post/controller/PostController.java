package com.example.blog.domain.post.controller;

import com.example.blog.domain.post.dto.PostCreateRequest;
import com.example.blog.domain.post.dto.PostListResponse;
import com.example.blog.domain.post.dto.PostResponse;
import com.example.blog.domain.post.dto.PostUpdateRequest;
import com.example.blog.domain.post.service.PostService;
import com.example.blog.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PostResponse> create(
        @RequestHeader("X-User-Id") Long userId,
        @RequestBody @Valid PostCreateRequest request
    ) {
        return ApiResponse.success(postService.create(userId, request));
    }

    @GetMapping
    public ApiResponse<PostListResponse> findAll(
        @RequestParam(required = false) String status,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.success(postService.findAll(status, page, size));
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponse> findById(@PathVariable Long postId) {
        return ApiResponse.success(postService.findById(postId));
    }

    @PatchMapping("/{postId}")
    public ApiResponse<PostResponse> update(
        @RequestHeader("X-User-Id") Long userId,
        @PathVariable Long postId,
        @RequestBody @Valid PostUpdateRequest request
    ) {
        return ApiResponse.success(postService.update(userId, postId, request));
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
        @RequestHeader("X-User-Id") Long userId,
        @PathVariable Long postId
    ) {
        postService.delete(userId, postId);
    }
}
