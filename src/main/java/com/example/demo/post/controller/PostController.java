package com.example.demo.post.controller;

import com.example.demo.global.exception.ApiResponse;
import com.example.demo.global.exception.BaseCode;
import com.example.demo.global.exception.ResponseUtil;
import com.example.demo.post.dto.PostCreateRequest;
import com.example.demo.post.dto.PostDetailResponse;
import com.example.demo.post.dto.PostResponse;
import com.example.demo.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Post", description = "게시글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // 게시글 생성
    @Operation(
            summary = "게시글 생성 API",
            description = "사용자가 새로운 게시글을 생성합니다."
    )
    @PostMapping
    public ApiResponse<PostResponse> create(
            @RequestBody @Valid PostCreateRequest request) {

        return ResponseUtil.success(
                BaseCode.POST_CREATE_SUCCESS,
                postService.createPost(request)
        );
    }

    // 게시글 수정
    @Operation(
            summary = "게시글 수정 API",
            description = "postId에 해당하는 게시글을 수정합니다."
    )
    @PutMapping("/{postId}")
    public ApiResponse<PostResponse> update(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId,

            @RequestBody @Valid PostCreateRequest request) {

        return ResponseUtil.success(
                BaseCode.POST_UPDATE_SUCCESS,
                postService.updatePost(postId, request)
        );
    }

    // 게시글 삭제
    @Operation(
            summary = "게시글 삭제 API",
            description = "postId에 해당하는 게시글을 삭제합니다."
    )
    @DeleteMapping("/{postId}")
    public ApiResponse<Map<String, Long>> delete(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId) {

        return ResponseUtil.success(
                BaseCode.POST_DELETE_SUCCESS,
                Map.of("postId", postService.deletePost(postId))
        );
    }

    // 게시글 조회
    @Operation(
            summary = "게시글 상세 조회 API",
            description = "postId에 해당하는 게시글 상세 정보를 조회합니다."
    )
    @GetMapping("/{postId}")
    public ApiResponse<PostDetailResponse> getPost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId) {

        return ResponseUtil.success(
                BaseCode.SUCCESS,
                postService.getPostDetail(postId)
        );
    }
}