package com.example.demo.post.controller;

import com.example.demo.global.exception.ApiResponse;
import com.example.demo.global.exception.BaseCode;
import com.example.demo.global.exception.ErrorResponse;
import com.example.demo.global.exception.ResponseUtil;
import com.example.demo.post.dto.PostCreateRequest;
import com.example.demo.post.dto.PostDetailResponse;
import com.example.demo.post.dto.PostResponse;
import com.example.demo.post.service.PostService;
import com.example.demo.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Post", description = "게시글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Operation(
            summary = "게시글 생성 API",
            description = "로그인한 사용자가 새로운 게시글을 생성합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "게시글 생성 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "유효성 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증되지 않은 사용자",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "미디어를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    public ApiResponse<PostResponse> create(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid PostCreateRequest request) {

        return ResponseUtil.success(
                BaseCode.POST_CREATE_SUCCESS,
                postService.createPost(user.getId(), request)
        );
    }

    @Operation(
            summary = "게시글 수정 API",
            description = "로그인한 사용자가 postId에 해당하는 게시글을 수정합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "게시글 수정 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "유효성 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증되지 않은 사용자",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "게시글 또는 미디어를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PutMapping("/{postId}")
    public ApiResponse<PostResponse> update(
            @AuthenticationPrincipal User user,

            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId,

            @RequestBody @Valid PostCreateRequest request) {

        return ResponseUtil.success(
                BaseCode.POST_UPDATE_SUCCESS,
                postService.updatePost(user.getId(), postId, request)
        );
    }

    @Operation(
            summary = "게시글 삭제 API",
            description = "로그인한 사용자가 postId에 해당하는 게시글을 삭제합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "게시글 삭제 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증되지 않은 사용자",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "게시글을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @DeleteMapping("/{postId}")
    public ApiResponse<Map<String, Long>> delete(
            @AuthenticationPrincipal User user,

            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId) {

        return ResponseUtil.success(
                BaseCode.POST_DELETE_SUCCESS,
                Map.of("postId", postService.deletePost(user.getId(), postId))
        );
    }

    @Operation(
            summary = "게시글 상세 조회 API",
            description = "postId에 해당하는 게시글 상세 정보를 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "게시글 상세 조회 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "게시글을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{postId}")
    public ApiResponse<PostDetailResponse> getPost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId) {

        return ResponseUtil.success(
                BaseCode.SUCCESS,
                postService.getPostDetail(postId)
        );
    }
}