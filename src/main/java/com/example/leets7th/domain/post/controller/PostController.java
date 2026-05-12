package com.example.leets7th.domain.post.controller;

import com.example.leets7th.domain.post.dto.req.PostRequestDTO;
import com.example.leets7th.domain.post.dto.res.PostResponseDTO;
import com.example.leets7th.domain.post.exception.code.PostSuccessCode;
import com.example.leets7th.domain.post.service.PostService;
import com.example.leets7th.global.apiPayload.ApiResponse;
import com.example.leets7th.global.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController implements PostControllerDocs {

    private final PostService postService;

    @GetMapping
    public ApiResponse<List<PostResponseDTO.PostListResDTO>> getPostList() {
        List<PostResponseDTO.PostListResDTO> result = postService.getPostList(SecurityUtil.getCurrentUserId());
        return ApiResponse.onSuccess(PostSuccessCode.GET_POSTLIST_SUCCESS, result);
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDTO.PostDetailResDTO> getPostDetail(@PathVariable Long postId) {
        PostResponseDTO.PostDetailResDTO result = postService.getPostDetail(postId, SecurityUtil.getCurrentUserId());
        return ApiResponse.onSuccess(PostSuccessCode.GET_POST_SUCCESS, result);
    }

    @PostMapping
    public ApiResponse<PostResponseDTO.CreatePostResDTO> createPost(@Valid @RequestBody PostRequestDTO.PostReqDTO req) {
        PostResponseDTO.CreatePostResDTO result = postService.createPost(SecurityUtil.getCurrentUserId(), req);
        return ApiResponse.onSuccess(PostSuccessCode.CREAT_POST_SUCCESS, result);
    }

    @PatchMapping("/{postId}")
    public ApiResponse<PostResponseDTO.PostDetailResDTO> patchPost(
            @PathVariable Long postId,
            @Valid @RequestBody PostRequestDTO.PostReqDTO req
    ) {
        PostResponseDTO.PostDetailResDTO result = postService.patchPost(postId, SecurityUtil.getCurrentUserId(), req);
        return ApiResponse.onSuccess(PostSuccessCode.PATCH_POST_SUCCESS, result);
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId, SecurityUtil.getCurrentUserId());
        return ApiResponse.onSuccess(PostSuccessCode.Delete_POST_SUCCESS, null);
    }
}
