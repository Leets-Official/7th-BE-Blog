package com.leets.blog.domain.like.controller;

import com.leets.blog.common.response.ApiResponse;
import com.leets.blog.domain.like.dto.CreatePostLikeRequest;
import com.leets.blog.domain.like.dto.PostLikeResponse;
import com.leets.blog.domain.like.service.PostLikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/likes")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    public ApiResponse<PostLikeResponse> createPostLike(
            @PathVariable Long postId,
            @RequestBody @Valid CreatePostLikeRequest request
    ) {
        return ApiResponse.onSuccess(postLikeService.createPostLike(postId, request));
    }
}
