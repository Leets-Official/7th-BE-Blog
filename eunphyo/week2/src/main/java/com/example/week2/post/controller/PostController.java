package com.example.week2.post.controller;

import com.example.week2.global.exception.ApiResponse;
import com.example.week2.post.dto.PostCreateRequest;
import com.example.week2.post.dto.PostResponse;
import com.example.week2.post.dto.PostUpdateRequest;
import com.example.week2.post.repository.PostRepository;
import com.example.week2.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.week2.post.dto.PostUpdateRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> createPost(
            @RequestParam Long userId,
            @Valid @RequestBody PostCreateRequest postCreateRequest
    ) {
        PostResponse postResponse = postService.createPost(userId, postCreateRequest);
        return ResponseEntity.ok(ApiResponse.success(postResponse));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @RequestParam Long userId,
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequest request
    ) {
        PostResponse response = postService.updatePost(userId, postId, request);
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

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Long postId) {
        PostResponse response = postService.getPost(postId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPosts() {
        List<PostResponse> responses = postService.getPosts();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

}