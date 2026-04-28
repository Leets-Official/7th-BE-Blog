package com.example.demo.controller;

import com.example.demo.domain.post.dto.*;
import com.example.demo.domain.post.service.PostService;
import com.example.demo.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<ApiResponse<PostListResponse>> getPosts(
            @Valid @ModelAttribute PostListRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("POST_LIST_SUCCESS", "게시글 목록 조회 성공", postService.getPosts(request.page(), request.size()))
        );
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(
                ApiResponse.success("POST_DETAIL_SUCCESS", "게시글 상세 조회 성공", postService.getPost(postId))
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PostCreateResponse>> createPost(
            @Valid @RequestBody PostCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "POST_CREATE_SUCCESS",
                        "게시글 생성 성공",
                        postService.createPost(request)
                ));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequest request
    ) {
        postService.updatePost(postId, request);
        return ResponseEntity.ok(
                ApiResponse.success("POST_UPDATE_SUCCESS", "게시글이 수정되었습니다.", null)
        );
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostDeleteRequest request
    ) {
        postService.deletePost(postId, request);
        return ResponseEntity.ok(
                ApiResponse.success("POST_DELETE_SUCCESS", "게시글이 삭제되었습니다.", null)
        );
    }
}
