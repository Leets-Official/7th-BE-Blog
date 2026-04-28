package com.example.leets7th.domain.post.controller;

import com.example.leets7th.domain.post.dto.request.PostCreateRequest;
import com.example.leets7th.domain.post.dto.request.PostUpdateRequest;
import com.example.leets7th.domain.post.dto.response.PostDetailResponse;
import com.example.leets7th.domain.post.dto.response.PostListResponse;
import com.example.leets7th.domain.post.service.PostService;
import com.example.leets7th.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Post", description = "게시글 관련 API")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Validated
public class PostController {

    private final PostService postService;

    // 게시글 목록 조회
    @Operation(summary = "게시글 목록 조회", description = "페이지 단위로 게시글 목록을 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<PostListResponse>> getPosts(
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.") int page,
            @Parameter(description = "페이지 크기", example = "10")
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.") int size
    ) {
        PostListResponse response = postService.getPosts(page, size);
        return ResponseEntity.ok(ApiResponse.success("POST_LIST_SUCCESS", "게시글 목록 조회 성공", response));
    }

    // 게시글 상세 조회
    @Operation(summary = "게시글 상세 조회", description = "게시글 ID로 단건 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> getPost(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId) {
        PostDetailResponse response = postService.getPost(postId);
        return ResponseEntity.ok(ApiResponse.success("POST_DETAIL_SUCCESS", "게시글 상세 조회 성공", response));
    }

    // 게시글 작성
    // TODO: 실제 인증 구현 시 @AuthenticationPrincipal로 userId 주입
    @Operation(summary = "게시글 작성", description = "새 게시글을 생성합니다. X-User-Id 헤더로 작성자를 지정합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "생성 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "입력값 오류")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> createPost(
            @RequestBody @Valid PostCreateRequest request,
            @Parameter(description = "작성자 ID (임시)", example = "1")
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId
    ) {
        Long postId = postService.createPost(request, userId);
        Map<String, Object> data = Map.of(
                "postId", postId,
                "message", "게시글이 생성되었습니다."
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(data));
    }

    // 게시글 수정
    @Operation(summary = "게시글 수정", description = "게시글 제목/내용을 수정합니다. 작성자만 수정 가능합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "수정 권한 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<Map<String, String>>> updatePost(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId,
            @RequestBody PostUpdateRequest request,
            @Parameter(description = "요청자 ID (임시)", example = "1")
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId
    ) {
        postService.updatePost(postId, request, userId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("message", "게시글이 수정되었습니다.")));
    }

    // 게시글 삭제
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다. 작성자만 삭제 가능합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "삭제 권한 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Map<String, String>>> deletePost(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId,
            @Parameter(description = "요청자 ID (임시)", example = "1")
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId
    ) {
        postService.deletePost(postId, userId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("message", "게시글이 삭제되었습니다.")));
    }
}
