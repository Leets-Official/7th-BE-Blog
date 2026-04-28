package com.example.leets7th.domain.comment.controller;

import com.example.leets7th.domain.comment.dto.req.CommentRequestDTO;
import com.example.leets7th.domain.comment.dto.res.CommentResponseDTO;
import com.example.leets7th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment", description = "댓글 관련 API")
public interface CommentControllerDocs {

    @Operation(
            summary = "댓글 목록 조회 api",
            description = "게시글의 댓글 목록을 대댓글과 함께 반환합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT200_1", description = "댓글 목록 조회에 성공했습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH401_1", description = "인증이 필요합니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST404_1", description = "해당 게시글이 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST403_2", description = "신고 처리된 게시글입니다."),
    })
    @GetMapping("/api/posts/{postId}/comments")
    ApiResponse<List<CommentResponseDTO.CommentResDTO>> getCommentList(
            @RequestHeader @Valid Long userId,
            @PathVariable Long postId
    );

    @Operation(
            summary = "댓글 작성 api",
            description = "게시글에 댓글을 작성합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT201_1", description = "댓글 작성에 성공했습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH401_1", description = "인증이 필요합니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST404_1", description = "해당 게시글이 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST403_2", description = "신고 처리된 게시글입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT400_1", description = "내용을 입력해주세요."),
    })
    @PostMapping("/api/posts/{postId}/comments")
    ApiResponse<CommentResponseDTO.CreateCommentResDTO> createComment(
            @RequestHeader @Valid Long userId,
            @PathVariable Long postId,
            @RequestBody @Valid CommentRequestDTO.CreateCommentDTO request
    );

    @Operation(
            summary = "대댓글 작성 api",
            description = "댓글에 대댓글을 작성합니다. 대댓글에는 답글을 달 수 없습니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT201_2", description = "대댓글 작성에 성공했습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH401_1", description = "인증이 필요합니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST404_1", description = "해당 게시글이 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST403_2", description = "신고 처리된 게시글입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT400_1", description = "내용을 입력해주세요."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT404_1", description = "해당 댓글이 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT400_2", description = "해당 댓글이 게시글에 속하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT400_3", description = "대댓글에는 답글을 달 수 없습니다."),
    })
    @PostMapping("/api/posts/{postId}/comments/{commentId}/replies")
    ApiResponse<CommentResponseDTO.CreateCommentResDTO> createReply(
            @RequestHeader @Valid Long userId,
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody @Valid CommentRequestDTO.CreateCommentDTO request
    );
}
