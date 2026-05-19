package com.example.week2.post.controller;

import com.example.week2.global.response.ApiResponse;
import com.example.week2.global.response.ErrorCode;
import com.example.week2.global.swagger.ApiErrorCodeExample;
import com.example.week2.post.dto.PostCreateRequest;
import com.example.week2.post.dto.PostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Post", description = "게시글 API")
public interface PostControllerDocs {

        @Operation(summary = "게시글 생성", description = "특정 게시글을 생성합니다.")
        @ApiErrorCodeExample({ErrorCode.POST_NOT_FOUND})
        @PostMapping
        ApiResponse<PostResponse.CreatePostResponse> createPost(
                @Parameter(description = "작성자 user ID", example = "1")
                @RequestParam Long userId,
                @Valid @RequestBody PostCreateRequest request
        );

        @Operation(summary = "게시글 수정", description = "특정 게시글을 수정합니다.")
        @ApiErrorCodeExample({ErrorCode.POST_NOT_FOUND})
        @PatchMapping("/{postId}")
        ApiResponse<PostResponse.PostDetailResponse> updatePost(
                @Parameter(description = "작성자 user ID", example = "1")
                @RequestParam Long userId,
                @Parameter(description = "게시글 post ID", example = "1")
                @PathVariable Long postId,
                @Valid @RequestBody PostCreateRequest request
        );

        @Operation(summary = "특정 게시글 조회", description = "특정 게시글을 조회합니다.")
        @ApiErrorCodeExample({ErrorCode.POST_NOT_FOUND})
        @GetMapping("/{postId}")
        ApiResponse<PostResponse.PostDetailResponse> getPost(
                @Parameter(description = "게시글 post ID", example = "1")
                @PathVariable Long postId
        );

        @Operation(summary = "게시글 목록 조회", description = "게시글 목록을 조회합니다.")
        @ApiErrorCodeExample({ErrorCode.POST_NOT_FOUND})
        @GetMapping
        ApiResponse<List<PostResponse.PostListResponse>> getPosts();

        @Operation(summary = "게시글 삭제", description = "특정 게시글을 삭제합니다.")
        @ApiErrorCodeExample({ErrorCode.POST_NOT_FOUND})
        @DeleteMapping("/{postId}")
        ApiResponse<Void> deletePost(
                @Parameter(description = "작성자 user ID", example = "1")
                @RequestParam Long userId,
                @Parameter(description = "게시글 post ID", example = "1")
                @PathVariable Long postId
        );
    }
