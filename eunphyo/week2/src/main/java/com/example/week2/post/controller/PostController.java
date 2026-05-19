package com.example.week2.post.controller;

import com.example.week2.global.response.ApiResponse;
import com.example.week2.global.response.SuccessCode;
import com.example.week2.post.dto.PostCreateRequest;
import com.example.week2.post.dto.PostResponse;
import com.example.week2.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController implements PostControllerDocs{

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PostResponse.CreatePostResponse> createPost(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody PostCreateRequest postCreateRequest
    ) {
        PostResponse.CreatePostResponse response =
                postService.createPost(userId, postCreateRequest);

        return ApiResponse.success(SuccessCode.POST_CREATED, response);
    }


    @PatchMapping("/{postId}")
    public ApiResponse<PostResponse.PostDetailResponse> updatePost(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long postId,
            @Valid @RequestBody PostCreateRequest postCreateRequest
    ) {
        PostResponse.PostDetailResponse response =
                postService.updatePost(userId, postId, postCreateRequest);

        return ApiResponse.success(SuccessCode.POST_UPDATED, response);
    }


    @GetMapping("/{postId}")
    public ApiResponse<PostResponse.PostDetailResponse> getPost(
            @PathVariable Long postId
    ) {
        PostResponse.PostDetailResponse response =
                postService.getPost(postId);

        return ApiResponse.success(SuccessCode.POST_DETAIL_GET, response);
    }


    @GetMapping
    public ApiResponse<List<PostResponse.PostListResponse>> getPosts() {
        List<PostResponse.PostListResponse> response =
                postService.getPosts();

        return ApiResponse.success(SuccessCode.POST_GET, response);
    }


    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long postId
    ) {
        postService.deletePost(userId, postId);
        return ApiResponse.success(SuccessCode.POST_DELETED, null);
    }
}