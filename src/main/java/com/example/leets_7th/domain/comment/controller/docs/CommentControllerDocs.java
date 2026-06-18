package com.example.leets_7th.domain.comment.controller.docs;

import com.example.leets_7th.common.auth.CustomUserDetails;
import com.example.leets_7th.common.response.ApiResponse;
import com.example.leets_7th.domain.comment.dto.request.CreateCommentRequest;
import com.example.leets_7th.domain.comment.dto.request.ReportCommentRequest;
import com.example.leets_7th.domain.comment.dto.request.UpdateCommentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comment", description = "댓글 관련 API")
public interface CommentControllerDocs {

    @PostMapping("/{postId}/comments")
    @Operation(summary = "댓글 작성", description = "특정 게시글에 댓글을 작성합니다.")
    @Parameters({
            @Parameter(name = "userId", description = "댓글 작성 사용자 ID", required = true, example = "1"),
            @Parameter(name = "postId", description = "게시글 ID", required = true, example = "10")
    })
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "댓글 작성 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            )
    })
    ResponseEntity<ApiResponse<Void>> createComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long postId,
            @RequestBody @Valid CreateCommentRequest request
    );

    @Operation(
            summary = "댓글 삭제",
            description = "특정 게시글의 댓글을 삭제합니다. 댓글 작성자만 삭제할 수 있습니다."
    )
    @Parameters({
            @Parameter(name = "userId", description = "댓글 삭제 요청 사용자 ID", required = true, example = "1"),
            @Parameter(name = "postId", description = "게시글 ID", required = true, example = "10"),
            @Parameter(name = "commentId", description = "삭제할 댓글 ID", required = true, example = "100")
    })
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "댓글 삭제 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "댓글 삭제 권한 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글, 댓글 또는 사용자를 찾을 수 없음")
    })
    @DeleteMapping("/{postId}/comments/{commentId}")
    ResponseEntity<ApiResponse<Void>> deleteComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long postId,
            @PathVariable Long commentId
    );

    @PatchMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "댓글 수정", description = "특정 댓글을 수정합니다.")
    @Parameters({
            @Parameter(name = "userId", description = "댓글 수정 사용자 ID", required = true, example = "1"),
            @Parameter(name = "postId", description = "게시글 ID", required = true, example = "10"),
            @Parameter(name = "commentId", description = "댓글 ID", required = true, example = "100")
    })
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "댓글 수정 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            )
    })
    ResponseEntity<ApiResponse<Void>> updateComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody @Valid UpdateCommentRequest request
    );

    @PostMapping("/{postId}/comments/{commentId}/likes")
    @Operation(summary = "댓글 좋아요", description = "특정 댓글에 좋아요를 추가합니다.")
    @Parameters({
            @Parameter(name = "userId", description = "좋아요 사용자 ID", required = true, example = "1"),
            @Parameter(name = "postId", description = "게시글 ID", required = true, example = "10"),
            @Parameter(name = "commentId", description = "댓글 ID", required = true, example = "100")
    })
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "댓글 좋아요 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            )
    })
    ResponseEntity<ApiResponse<Void>> likeComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long postId,
            @PathVariable Long commentId
    );

    @DeleteMapping("/{postId}/comments/{commentId}/likes")
    @Operation(summary = "댓글 좋아요 취소", description = "특정 댓글의 좋아요를 취소합니다.")
    @Parameters({
            @Parameter(name = "userId", description = "좋아요 취소 사용자 ID", required = true, example = "1"),
            @Parameter(name = "postId", description = "게시글 ID", required = true, example = "10"),
            @Parameter(name = "commentId", description = "댓글 ID", required = true, example = "100")
    })
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "댓글 좋아요 취소 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            )
    })
    ResponseEntity<ApiResponse<Void>> unlikeComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long postId,
            @PathVariable Long commentId
    );

    @Operation(
            summary = "댓글 신고",
            description = "특정 게시글의 댓글을 신고합니다. 한 사용자는 같은 댓글을 한 번만 신고할 수 있습니다."
    )
    @Parameters({
            @Parameter(name = "userId", description = "신고하는 사용자 ID", required = true, example = "1"),
            @Parameter(name = "postId", description = "게시글 ID", required = true, example = "10"),
            @Parameter(name = "commentId", description = "신고할 댓글 ID", required = true, example = "100")
    })
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "댓글 신고 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "이미 신고한 댓글이거나 잘못된 요청값"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글, 댓글 또는 사용자를 찾을 수 없음")
    })
    @PostMapping("/{postId}/comments/{commentId}/reports")
    ResponseEntity<ApiResponse<Void>> reportComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody @Valid ReportCommentRequest request
    );
}
