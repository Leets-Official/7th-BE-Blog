package com.example.leets7th.domain.post.controller;

import com.example.leets7th.domain.post.dto.req.PostRequestDTO;
import com.example.leets7th.domain.post.dto.res.PostResponseDTO;
import com.example.leets7th.domain.post.exception.code.PostSuccessCode;
import com.example.leets7th.domain.post.service.PostService;
import com.example.leets7th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController implements PostControllerDocs{
    private final PostService postService;

    // 게시글 목록 조회 API
    @GetMapping
    public ApiResponse<List<PostResponseDTO.PostListResDTO>> getPostList(
            @RequestHeader @Valid Long userId
    ) {
        List<PostResponseDTO.PostListResDTO> result = postService.getPostList(userId);

        return ApiResponse.onSuccess(PostSuccessCode.GET_POSTLIST_SUCCESS, result);
    }

    // 게시글 상세 조회 API
    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDTO.PostDetailResDTO> getPostDetail(
            @PathVariable Long postId,
            @RequestHeader @Valid Long userId
    ) {
        PostResponseDTO.PostDetailResDTO result = postService.getPostDetail(postId, userId);

        return ApiResponse.onSuccess(PostSuccessCode.GET_POST_SUCCESS, result);
    }

    // 게시글 작성 API
    @PostMapping
    public ApiResponse<PostResponseDTO.CreatePostResDTO> createPost(
            @RequestHeader @Valid Long userId,
            @Valid @RequestBody PostRequestDTO.PostReqDTO req
    ) {
        PostResponseDTO.CreatePostResDTO result = postService.createPost(userId, req);
        return ApiResponse.onSuccess(PostSuccessCode.CREAT_POST_SUCCESS, result);
    }
}
