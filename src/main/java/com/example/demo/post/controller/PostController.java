package com.example.demo.post.controller;

import com.example.demo.global.exception.ApiResponse;
import com.example.demo.global.exception.BaseCode;
import com.example.demo.global.exception.ErrorResponse;
import com.example.demo.global.exception.ResponseUtil;
import com.example.demo.post.dto.PostCreateRequest;
import com.example.demo.post.dto.PostDetailResponse;
import com.example.demo.post.dto.PostResponse;
import com.example.demo.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Post", description = "게시글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // 게시글 생성
    @Operation(
            summary = "게시글 생성 API",
            description = "사용자가 새로운 게시글을 생성합니다."
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
                    responseCode = "404",
                    description = "유저 또는 미디어를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    public ApiResponse<PostResponse> create(
            @RequestBody @Valid PostCreateRequest request) {

        return ResponseUtil.success(
                BaseCode.POST_CREATE_SUCCESS,
                postService.createPost(request)
        );
    }

    // 게시글 수정
    @Operation(
            summary = "게시글 수정 API",
            description = "postId에 해당하는 게시글을 수정합니다."
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
                    responseCode = "404",
                    description = "게시글 또는 미디어를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PutMapping("/{postId}")
    public ApiResponse<PostResponse> update(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId,

            @RequestBody @Valid PostCreateRequest request) {

        return ResponseUtil.success(
                BaseCode.POST_UPDATE_SUCCESS,
                postService.updatePost(postId, request)
        );
    }

    // 게시글 삭제
    @Operation(
            summary = "게시글 삭제 API",
            description = "postId에 해당하는 게시글을 삭제합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "게시글 삭제 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "게시글을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @DeleteMapping("/{postId}")
    public ApiResponse<Map<String, Long>> delete(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId) {

        return ResponseUtil.success(
                BaseCode.POST_DELETE_SUCCESS,
                Map.of("postId", postService.deletePost(postId))
        );
    }

    // 게시글 조회
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