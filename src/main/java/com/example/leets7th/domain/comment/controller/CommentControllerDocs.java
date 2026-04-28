package com.example.leets7th.domain.comment.controller;

import com.example.leets7th.domain.comment.dto.CommentCreateRequest;
import com.example.leets7th.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@Tag(name = "Comment", description = "댓글 관련 API")
public interface CommentControllerDocs {

    @Operation(summary = "댓글 작성", description = "게시글에 댓글을 작성합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "작성 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    ResponseEntity<ApiResponse<Map<String, Object>>> createComment(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId,
            @RequestBody @Valid CommentCreateRequest request,
            @Parameter(description = "작성자 ID (임시)", example = "1")
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId
    );

    @Operation(summary = "댓글 채택", description = "게시글 작성자가 댓글을 채택합니다. 게시글당 하나의 댓글만 채택 가능합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "채택 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "해당 게시글의 댓글이 아님"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "채택 권한 없음 (게시글 작성자가 아님)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글 또는 댓글 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 채택된 댓글 존재")
    })
    ResponseEntity<ApiResponse<Map<String, String>>> adoptComment(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId,
            @Parameter(description = "댓글 ID", example = "1") @PathVariable Long commentId,
            @Parameter(description = "요청자 ID (임시)", example = "1")
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId
    );
}
