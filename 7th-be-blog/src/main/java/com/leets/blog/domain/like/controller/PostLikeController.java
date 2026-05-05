package com.leets.blog.domain.like.controller;

import com.leets.blog.common.response.ApiResponse;
import com.leets.blog.domain.like.dto.CreatePostLikeRequest;
import com.leets.blog.domain.like.dto.PostLikeResponse;
import com.leets.blog.domain.like.service.PostLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Post Likes", description = "게시글 좋아요 API")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    @Operation(summary = "게시글 좋아요", description = "특정 게시글에 좋아요를 등록합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "좋아요 등록 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글 또는 사용자를 찾을 수 없음", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 좋아요한 게시글", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class)))
    })
    public ApiResponse<PostLikeResponse> createPostLike(
            @PathVariable Long postId,
            @RequestBody @Valid CreatePostLikeRequest request
    ) {
        return ApiResponse.onSuccess(postLikeService.createPostLike(postId, request));
    }
}
