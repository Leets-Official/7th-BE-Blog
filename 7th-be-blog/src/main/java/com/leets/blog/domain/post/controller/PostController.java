package com.leets.blog.domain.post.controller;

import com.leets.blog.common.response.ApiResponse;
import com.leets.blog.domain.post.dto.CreatePostRequest;
import com.leets.blog.domain.post.dto.PostResponse;
import com.leets.blog.domain.post.dto.UpdatePostRequest;
import com.leets.blog.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Posts", description = "게시글 CRUD API")
public class PostController {

    private final PostService postService;

    @GetMapping
    @Operation(summary = "게시글 목록 조회", description = "전체 게시글 목록을 조회합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공")
    public ApiResponse<List<PostResponse>> getPostList() {
        return ApiResponse.onSuccess(postService.getPostList());
    }

    @GetMapping("/{postId}")
    @Operation(summary = "게시글 상세 조회", description = "게시글 ID로 단건 게시글을 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 상세 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class)))
    })
    public ApiResponse<PostResponse> getPostDetail(@PathVariable Long postId) {
        return ApiResponse.onSuccess(postService.getPostDetail(postId));
    }

    @PostMapping
    @Operation(summary = "게시글 생성", description = "새 게시글을 생성합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 생성 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class)))
    })
    public ApiResponse<PostResponse> createPost(@RequestBody @Valid CreatePostRequest request) {
        return ApiResponse.onSuccess(postService.createPost(request));
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "게시글 수정", description = "게시글 ID로 게시글을 수정합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class)))
    })
    public ApiResponse<PostResponse> updatePost(@PathVariable Long postId, @RequestBody @Valid UpdatePostRequest request) {
        return ApiResponse.onSuccess(postService.updatePost(postId, request));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 삭제", description = "게시글 ID로 게시글을 삭제합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content(schema = @Schema(implementation = com.leets.blog.common.response.ErrorResponse.class)))
    })
    public ApiResponse<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ApiResponse.onSuccess("게시글이 성공적으로 삭제되었습니다.");
    }
}
