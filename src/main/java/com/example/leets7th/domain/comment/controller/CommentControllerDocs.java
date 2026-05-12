package com.example.leets7th.domain.comment.controller;

import com.example.leets7th.domain.comment.dto.req.CommentRequestDTO;
import com.example.leets7th.domain.comment.dto.res.CommentResponseDTO;
import com.example.leets7th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment", description = "댓글 관련 API")
public interface CommentControllerDocs {

    @Operation(summary = "댓글 목록 조회 api", description = "게시글의 댓글 목록을 대댓글과 함께 반환합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT200_1", description = "댓글 목록 조회에 성공했습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH403_1", description = "인증이 필요합니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST404_1", description = "해당 게시글이 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST403_2", description = "신고 처리된 게시글입니다.")
    })
    @GetMapping("/api/posts/{postId}/comments")
    ApiResponse<List<CommentResponseDTO.CommentResDTO>> getCommentList(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId
    );

    @Operation(summary = "댓글 채택 api", description = "게시글 작성자가 댓글을 채택합니다. 자신의 댓글은 채택 불가, 게시글당 1회만 가능합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT200_2", description = "댓글 채택에 성공했습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH403_1", description = "인증이 필요합니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT403_1", description = "본인의 게시글에서만 채택이 가능합니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT400_4", description = "자신의 댓글은 채택할 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT400_5", description = "이미 채택된 댓글이 있는 게시글입니다.")
    })
    @PatchMapping("/api/posts/{postId}/comments/{commentId}/adopt")
    ApiResponse<Void> adoptComment(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId,
            @Parameter(description = "채택할 댓글 ID", example = "10") @PathVariable Long commentId
    );

    @Operation(summary = "댓글 작성 api", description = "게시글에 댓글을 작성합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT201_1", description = "댓글 작성에 성공했습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH403_1", description = "인증이 필요합니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST404_1", description = "해당 게시글이 존재하지 않습니다.")
    })
    @PostMapping("/api/posts/{postId}/comments")
    ApiResponse<CommentResponseDTO.CreateCommentResDTO> createComment(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId,
            @RequestBody @Valid CommentRequestDTO.CreateCommentDTO request
    );

    @Operation(summary = "대댓글 작성 api", description = "댓글에 대댓글을 작성합니다. 대댓글에는 답글을 달 수 없습니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT201_2", description = "대댓글 작성에 성공했습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH403_1", description = "인증이 필요합니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT400_3", description = "대댓글에는 답글을 달 수 없습니다.")
    })
    @PostMapping("/api/posts/{postId}/comments/{commentId}/replies")
    ApiResponse<CommentResponseDTO.CreateCommentResDTO> createReply(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId,
            @Parameter(description = "부모 댓글 ID", example = "10") @PathVariable Long commentId,
            @RequestBody @Valid CommentRequestDTO.CreateCommentDTO request
    );
}
