package com.example.leets7th.domain.post.controller;

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

    @GetMapping
    public ApiResponse<List<PostResponseDTO.PostListResDTO>> getPostList(
            @RequestHeader @Valid Long userId
    ) {
        List<PostResponseDTO.PostListResDTO> result = postService.getPostList(userId);

        return ApiResponse.onSuccess(PostSuccessCode.GET_POSTLIST_SUCCESS, result);
    }
}
