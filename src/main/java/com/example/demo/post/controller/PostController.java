package com.example.demo.post.controller;

import com.example.demo.global.exception.ApiResponse;
import com.example.demo.global.exception.BaseCode;
import com.example.demo.global.exception.ResponseUtil;
import com.example.demo.post.dto.PostCreateRequest;
import com.example.demo.post.dto.PostDetailResponse;
import com.example.demo.post.dto.PostResponse;
import com.example.demo.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    //게시글 생성
    @PostMapping
    public ApiResponse<PostResponse> create(
            @RequestBody @Valid PostCreateRequest request) {

        return ResponseUtil.success(
                BaseCode.POST_CREATE_SUCCESS,
                postService.createPost(request)
        );
    }

    //게시글 수정
    @PutMapping("/{postId}")
    public ApiResponse<PostResponse> update(
            @PathVariable Long postId,
            @RequestBody @Valid PostCreateRequest request) {

        return ResponseUtil.success(
                BaseCode.POST_UPDATE_SUCCESS,
                postService.updatePost(postId, request)
        );
    }

    //게시글 삭제
    @DeleteMapping("/{postId}")
    public ApiResponse<Map<String, Long>> delete(
            @PathVariable Long postId) {

        return ResponseUtil.success(
                BaseCode.POST_DELETE_SUCCESS,
                Map.of("postId", postService.deletePost(postId))
        );
    }

    //게시글 조회
    @GetMapping("/{postId}")
    public ApiResponse<PostDetailResponse> getPost(@PathVariable Long postId) {

        return ResponseUtil.success(
                BaseCode.SUCCESS,
                postService.getPostDetail(postId)
        );
    }
}