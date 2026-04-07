package com.example.springbootassignment.controller;

import com.example.springbootassignment.dto.PostCreateRequest;
import com.example.springbootassignment.dto.PostListResponse;
import com.example.springbootassignment.dto.PostResponse;
import com.example.springbootassignment.dto.PostUpdateRequest;
import com.example.springbootassignment.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * 게시글 목록 조회
     * GET /posts?page=1&size=10&sort=createdAt&order=desc
     */
    @GetMapping
    public ResponseEntity<PostListResponse> getPostList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String order) {
        PostListResponse response = postService.getPostList(page, size, sort, order);
        return ResponseEntity.ok(response);
    }

    /**
     * 게시글 상세 조회
     * GET /posts/{postId}
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostDetail(@PathVariable Long postId) {
        PostResponse response = postService.getPostDetail(postId);
        return ResponseEntity.ok(response);
    }

    /**
     * 게시글 작성
     * POST /posts
     */
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostCreateRequest request) {
        PostResponse response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 게시글 수정
     * PUT /posts/{postId}
     */
    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequest request) {
        PostResponse response = postService.updatePost(postId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 게시글 삭제
     * DELETE /posts/{postId}
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
