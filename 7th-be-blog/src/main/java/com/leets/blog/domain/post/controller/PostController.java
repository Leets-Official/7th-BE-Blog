package com.leets.blog.domain.post.controller;

import com.leets.blog.common.response.ApiResponse;
import com.leets.blog.domain.post.dto.CreatePostRequest;
import com.leets.blog.domain.post.dto.PostResponse;
import com.leets.blog.domain.post.dto.UpdatePostRequest;
import com.leets.blog.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ApiResponse<List<PostResponse>> getPostList() {
        return ApiResponse.onSuccess(postService.getPostList());
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponse> getPostDetail(@PathVariable Long postId) {
        return ApiResponse.onSuccess(postService.getPostDetail(postId));
    }

    @PostMapping
    public ApiResponse<PostResponse> createPost(@RequestBody @Valid CreatePostRequest request) {
        return ApiResponse.onSuccess(postService.createPost(request));
    }

    @PatchMapping("/{postId}")
    public ApiResponse<PostResponse> updatePost(@PathVariable Long postId, @RequestBody @Valid UpdatePostRequest request) {
        return ApiResponse.onSuccess(postService.updatePost(postId, request));
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ApiResponse.onSuccess("게시글이 성공적으로 삭제되었습니다.");
    }
}
