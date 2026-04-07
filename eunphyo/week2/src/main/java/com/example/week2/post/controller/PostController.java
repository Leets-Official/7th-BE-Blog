package com.example.week2.post.controller;

import com.example.week2.global.exception.ApiResponse;
import com.example.week2.post.dto.PostCreateRequest;
import com.example.week2.post.dto.PostResponse;
import com.example.week2.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse.CreatePostResponse>> createPost(
            @RequestParam Long userId,
            @Valid @RequestBody PostCreateRequest postCreateRequest
    ) {
        PostResponse.CreatePostResponse response =
                postService.createPost(userId, postCreateRequest);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse.PostDetailResponse>> updatePost(
            @RequestParam Long userId,
            @PathVariable Long postId,
            @Valid @RequestBody PostCreateRequest postCreateRequest
    ) {
        PostResponse.PostDetailResponse response =
                postService.updatePost(userId, postId, postCreateRequest);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse.PostDetailResponse>> getPost(
            @PathVariable Long postId
    ) {
        PostResponse.PostDetailResponse response =
                postService.getPost(postId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse.PostListResponse>>> getPosts() {
        List<PostResponse.PostListResponse> response =
                postService.getPosts();

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @RequestParam Long userId,
            @PathVariable Long postId
    ) {
        postService.deletePost(userId, postId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}