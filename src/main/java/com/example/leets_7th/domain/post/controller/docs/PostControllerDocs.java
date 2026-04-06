package com.example.leets_7th.domain.post.controller.docs;

import com.example.leets_7th.common.response.ApiResponse;
import com.example.leets_7th.domain.post.dto.request.CreatePostRequest;
import com.example.leets_7th.domain.post.dto.request.UpdatePostRequest;
import com.example.leets_7th.domain.post.dto.response.CreatePostResponse;
import com.example.leets_7th.domain.post.dto.response.GetPostDetailResponse;
import com.example.leets_7th.domain.post.dto.response.GetPostResponse;
import com.example.leets_7th.domain.post.dto.response.UpdatePostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post", description = "게시글 관련 API")
public interface PostControllerDocs {

    @Operation(summary = "게시글 목록 조회", description = "게시글 리스트를 조회합니다.")
    @GetMapping
    ResponseEntity<ApiResponse<GetPostResponse>> getAllPost(
            @RequestParam Long userId,
            @RequestParam int page,
            @RequestParam int size
    );

    @Operation(summary = "게시글 상세 조회", description = "특정 게시글의 상세 정보를 조회합니다.")
    @GetMapping("/{postId}")
    ResponseEntity<ApiResponse<GetPostDetailResponse>> getDetailPost(
            @RequestParam Long userId,
            @Valid @PathVariable Long postId
    );

    @Operation(summary = "게시글 생성", description = "새로운 게시글을 생성합니다.")
    @PostMapping
    ResponseEntity<ApiResponse<CreatePostResponse>> createPost(
            @RequestParam Long userId,
            @RequestBody @Valid CreatePostRequest request
    );

    @Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다.")
    @PatchMapping("/{postId}")
    ResponseEntity<ApiResponse<UpdatePostResponse>> updatePost(
            @RequestParam Long userId,
            @PathVariable Long postId,
            @Valid @RequestBody UpdatePostRequest request
    );

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @DeleteMapping("/{postId}")
    ResponseEntity<ApiResponse<Void>> deletePost(
            @RequestParam Long userId,
            @Valid @PathVariable Long postId
    );
}
