package com.example.leets_7th.domain.post.controller;

import com.example.leets_7th.common.response.ApiResponse;
import com.example.leets_7th.common.status.SuccessStatus;
import com.example.leets_7th.domain.post.controller.docs.PostControllerDocs;
import com.example.leets_7th.domain.post.dto.request.CreatePostRequest;
import com.example.leets_7th.domain.post.dto.request.GetPostRequest;
import com.example.leets_7th.domain.post.dto.request.UpdatePostRequest;
import com.example.leets_7th.domain.post.dto.response.CreatePostResponse;
import com.example.leets_7th.domain.post.dto.response.GetPostDetailResponse;
import com.example.leets_7th.domain.post.dto.response.GetPostResponse;
import com.example.leets_7th.domain.post.dto.response.UpdatePostResponse;
import com.example.leets_7th.domain.post.service.PostCommandService;
import com.example.leets_7th.domain.post.service.PostQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/posts")
@RestController
@Validated
@RequiredArgsConstructor
public class PostController implements PostControllerDocs {

    private final PostCommandService postCommandService;
    private final PostQueryService postQueryService;

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<GetPostResponse>> getAllPost(
            @Valid @ModelAttribute GetPostRequest request
    ) {
        GetPostResponse response = postQueryService.getAllPost(request);
        return ApiResponse.success(SuccessStatus.GET_ALL_POST_SUCCESS, response);
    }

    @Override
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<GetPostDetailResponse>> getDetailPost(
            @RequestParam Long userId,
            @PathVariable Long postId
    ) {
        GetPostDetailResponse response = postQueryService.getPostDetail(userId, postId);
        return ApiResponse.success(SuccessStatus.GET_POST_DETAIL_SUCCESS, response);
    }

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<CreatePostResponse>> createPost(
            @RequestParam Long userId,
            @RequestBody @Valid CreatePostRequest request
    ) {
        CreatePostResponse response = postCommandService.createPost(userId, request);
        return ApiResponse.success(SuccessStatus.CREATE_POST_SUCCESS, response);
    }

    @Override
    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<UpdatePostResponse>> updatePost(
            @RequestParam Long userId,
            @PathVariable Long postId,
            @Valid @RequestBody UpdatePostRequest request
    ) {
        UpdatePostResponse response = postCommandService.updatePost(userId, postId, request);
        return ApiResponse.success(SuccessStatus.UPDATE_POST_SUCCESS, response);
    }

    @Override
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @RequestParam Long userId,
            @Valid @PathVariable Long postId
    ) {
        postCommandService.deletePost(userId, postId);
        return ApiResponse.success(SuccessStatus.DELETE_POST_SUCCESS);
    }
}
