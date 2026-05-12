package com.example.leets7th.domain.post.controller;

import com.example.leets7th.domain.post.dto.request.PostCreateRequest;
import com.example.leets7th.domain.post.dto.request.PostUpdateRequest;
import com.example.leets7th.domain.post.dto.response.PostDetailResponse;
import com.example.leets7th.domain.post.dto.response.PostListResponse;
import com.example.leets7th.domain.post.service.PostService;
import com.example.leets7th.global.common.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Validated
public class PostController implements PostControllerDocs {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<ApiResponse<PostListResponse>> getPosts(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.") int size
    ) {
        return ResponseEntity.ok(ApiResponse.success("POST_LIST_SUCCESS", "게시글 목록 조회 성공", postService.getPosts(page, size)));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(ApiResponse.success("POST_DETAIL_SUCCESS", "게시글 상세 조회 성공", postService.getPost(postId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> createPost(
            @RequestBody @Valid PostCreateRequest request,
            @AuthenticationPrincipal Long userId
    ) {
        Long postId = postService.createPost(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(Map.of("postId", postId, "message", "게시글이 생성되었습니다.")));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<Map<String, String>>> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequest request,
            @AuthenticationPrincipal Long userId
    ) {
        postService.updatePost(postId, request, userId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("message", "게시글이 수정되었습니다.")));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Map<String, String>>> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal Long userId
    ) {
        postService.deletePost(postId, userId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("message", "게시글이 삭제되었습니다.")));
    }

    @PatchMapping("/{postId}/hide")
    public ResponseEntity<ApiResponse<Map<String, String>>> hidePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal Long userId
    ) {
        postService.hidePost(postId, userId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("message", "게시글이 숨김 처리되었습니다.")));
    }
}
