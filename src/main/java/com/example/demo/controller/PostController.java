package com.example.demo.controller;

import com.example.demo.domain.post.dto.*;
import com.example.demo.domain.post.service.PostService;
import com.example.demo.global.response.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Validated
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<ApiResponse<PostListResponse>> getPosts(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.") int size
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("POST_LIST_SUCCESS", "게시글 목록 조회 성공", postService.getPosts(page, size))
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
