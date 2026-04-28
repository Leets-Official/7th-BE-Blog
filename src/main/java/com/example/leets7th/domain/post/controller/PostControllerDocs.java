package com.example.leets7th.domain.post.controller;

import com.example.leets7th.domain.post.dto.request.PostCreateRequest;
import com.example.leets7th.domain.post.dto.request.PostUpdateRequest;
import com.example.leets7th.domain.post.dto.response.PostDetailResponse;
import com.example.leets7th.domain.post.dto.response.PostListResponse;
import com.example.leets7th.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Tag(name = "Post", description = "게시글 관련 API")
public interface PostControllerDocs {

    @Operation(summary = "게시글 목록 조회", description = "페이지 단위로 게시글 목록을 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공")
    })
    ResponseEntity<ApiResponse<PostListResponse>> getPosts(
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.") int page,
            @Parameter(description = "페이지 크기", example = "10")
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.") int size
    );

    @Operation(summary = "게시글 상세 조회", description = "게시글 ID로 단건 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    ResponseEntity<ApiResponse<PostDetailResponse>> getPost(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId
    );

    @Operation(summary = "게시글 작성", description = "새 게시글을 생성합니다. X-User-Id 헤더로 작성자를 지정합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "생성 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "입력값 오류")
    })
    ResponseEntity<ApiResponse<Map<String, Object>>> createPost(
            @RequestBody @Valid PostCreateRequest request,
            @Parameter(description = "작성자 ID (임시)", example = "1")
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId
    );

    @Operation(summary = "게시글 수정", description = "게시글 제목/내용을 수정합니다. 작성자만 수정 가능합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "수정 권한 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    ResponseEntity<ApiResponse<Map<String, String>>> updatePost(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId,
            @RequestBody PostUpdateRequest request,
            @Parameter(description = "요청자 ID (임시)", example = "1")
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId
    );

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다. 작성자만 삭제 가능합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "삭제 권한 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    ResponseEntity<ApiResponse<Map<String, String>>> deletePost(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId,
            @Parameter(description = "요청자 ID (임시)", example = "1")
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId
    );

    @Operation(summary = "게시글 숨김", description = "게시글 상태를 ACTIVE에서 HIDDEN으로 변경합니다. 작성자만 가능합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "숨김 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "숨김 권한 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 숨김 처리된 게시글")
    })
    ResponseEntity<ApiResponse<Map<String, String>>> hidePost(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId,
            @Parameter(description = "요청자 ID (임시)", example = "1")
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId
    );
}
