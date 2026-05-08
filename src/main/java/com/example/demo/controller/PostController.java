package com.example.demo.controller;

import com.example.demo.domain.post.dto.*;
import com.example.demo.domain.post.service.PostService;
import com.example.demo.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post", description = "게시글 API")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 목록 조회", description = "페이지 번호와 크기를 기준으로 게시글 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<PostListResponse>> getPosts(
            @Valid @ModelAttribute PostListRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("POST_LIST_SUCCESS", "게시글 목록 조회 성공", postService.getPosts(request.page(), request.size()))
        );
    }

    @Operation(summary = "게시글 상세 조회", description = "게시글 ID로 상세 정보를 조회합니다.")
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> getPost(
            @Parameter(description = "조회할 게시글 ID", example = "1")
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("POST_DETAIL_SUCCESS", "게시글 상세 조회 성공", postService.getPost(postId))
        );
    }

    @Operation(summary = "게시글 생성", description = "새 게시글을 생성합니다.")
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

    @Operation(summary = "게시글 수정", description = "게시글 작성자만 게시글을 수정할 수 있습니다.")
    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(
            @Parameter(description = "수정할 게시글 ID", example = "1")
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequest request
    ) {
        postService.updatePost(postId, request);
        return ResponseEntity.ok(
                ApiResponse.success("POST_UPDATE_SUCCESS", "게시글이 수정되었습니다.", null)
        );
    }

    @Operation(summary = "게시글 삭제", description = "게시글 작성자만 게시글을 삭제할 수 있습니다.")
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @Parameter(description = "삭제할 게시글 ID", example = "1")
            @PathVariable Long postId,
            @Valid @RequestBody PostDeleteRequest request
    ) {
        postService.deletePost(postId, request);
        return ResponseEntity.ok(
                ApiResponse.success("POST_DELETE_SUCCESS", "게시글이 삭제되었습니다.", null)
        );
    }
}
