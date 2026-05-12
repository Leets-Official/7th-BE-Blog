package com.example.demo.comment.controller;

import com.example.demo.comment.dto.CommentCreateRequest;
import com.example.demo.comment.dto.CommentResponse;
import com.example.demo.comment.dto.CommentUpdateRequest;
import com.example.demo.comment.service.CommentService;
import com.example.demo.global.exception.ApiResponse;
import com.example.demo.global.exception.BaseCode;
import com.example.demo.global.exception.ResponseUtil;
import com.example.demo.global.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Comment", description = "댓글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @Operation(
            summary = "댓글 생성 API",
            description = "로그인한 사용자가 게시글에 댓글을 생성합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "댓글 생성 성공"
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
                    description = "게시글을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    public ApiResponse<Map<String, Long>> create(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid CommentCreateRequest request) {

        Long commentId = commentService.createComment(userId, request);

        return ResponseUtil.success(
                BaseCode.COMMENT_CREATE_SUCCESS,
                Map.of("commentId", commentId)
        );
    }

    // 특정 게시글 댓글 조회
    @Operation(
            summary = "특정 게시글 댓글 조회 API",
            description = "postId에 해당하는 게시글의 댓글 목록을 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "댓글 조회 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "게시글을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/post/{postId}")
    public ApiResponse<List<CommentResponse>> getComments(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId) {

        return ResponseUtil.success(
                BaseCode.SUCCESS,
                commentService.getComments(postId)
        );
    }

    // 댓글 수정
    @Operation(
            summary = "댓글 수정 API",
            description = "commentId에 해당하는 댓글 내용을 수정합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "댓글 수정 성공"
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
                    description = "댓글을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PutMapping("/{commentId}")
    public ApiResponse<Void> update(
            @Parameter(description = "댓글 ID", example = "1")
            @PathVariable Long commentId,
            @RequestBody @Valid CommentUpdateRequest request) {

        commentService.updateComment(commentId, request);

        return ResponseUtil.success(
                BaseCode.COMMENT_UPDATE_SUCCESS,
                null
        );
    }

    // 댓글 삭제
    @Operation(
            summary = "댓글 삭제 API",
            description = "commentId에 해당하는 댓글을 삭제합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "댓글 삭제 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증되지 않은 사용자",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "댓글을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @DeleteMapping("/{commentId}")
    public ApiResponse<Map<String, Long>> delete(
            @Parameter(description = "댓글 ID", example = "1")
            @PathVariable Long commentId) {

        commentService.deleteComment(commentId);

        return ResponseUtil.success(
                BaseCode.COMMENT_DELETE_SUCCESS,
                Map.of("commentId", commentId)
        );
    }
}