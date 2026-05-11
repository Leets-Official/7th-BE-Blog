package com.leets.blog.post.controller;

import com.leets.blog.global.common.BaseResponse;
import com.leets.blog.post.dto.PostRequest;
import com.leets.blog.post.dto.PostResponse;
import com.leets.blog.post.service.PostService;
import com.leets.blog.user.auth.AuthUser;
import com.leets.blog.user.auth.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts") // 모든 게시글 API는 /api/posts로 시작합니다.
@Tag(name = "Post", description = "게시글 CRUD 및 상태 전이(HIDE/ACTIVATE) API")
public class PostController {

    private final PostService postService;

    // 게시글 생성 (POST /api/posts)
    @PostMapping
    @Operation(
            summary = "게시글 생성",
            description = "새 게시글을 생성합니다. 임시 인증으로 헤더 `X-USER-ID` 값을 사용합니다."
    )
    public ResponseEntity<BaseResponse<PostResponse>> create(
            @Parameter(hidden = true)
            @CurrentUser AuthUser authUser,
            @Valid @RequestBody PostRequest.Create request
    ) {
        PostResponse response = postService.create(authUser, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.ok(response));
    }

    // 게시글 단건 조회 (GET /api/posts/{id})
    @GetMapping("/{id}")
    @Operation(
            summary = "게시글 단건 조회",
            description = "게시글 ID로 게시글을 조회합니다."
    )
    public ResponseEntity<BaseResponse<PostResponse>> findById(@PathVariable("id") Long id) { // ("id") 추가로 Swagger 연동
        PostResponse response = postService.findById(id);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }

    // 게시글 전체 조회 (GET /api/posts)
    @GetMapping
    @Operation(
            summary = "게시글 전체 조회",
            description = "전체 게시글 목록을 조회합니다."
    )
    public ResponseEntity<BaseResponse<List<PostResponse>>> findAll() {
        List<PostResponse> responses = postService.findAll();
        return ResponseEntity.ok(BaseResponse.ok(responses));
    }

    // 게시글 수정 (PATCH /api/posts/{id})
    @PatchMapping("/{id}")
    @Operation(
            summary = "게시글 수정",
            description = "게시글 제목/내용을 수정합니다. 작성자 본인만 수정 가능합니다."
    )
    public ResponseEntity<BaseResponse<PostResponse>> update(
            @Parameter(hidden = true)
            @CurrentUser AuthUser authUser,
            @PathVariable("id") Long id,
            @Valid @RequestBody PostRequest.Update request
    ) {
        PostResponse response = postService.update(authUser, id, request);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }

    // 게시글 삭제 (DELETE /api/posts/{id})
    @DeleteMapping("/{id}")
    @Operation(
            summary = "게시글 삭제",
            description = "게시글을 삭제합니다. 작성자 본인만 삭제 가능합니다."
    )
    public ResponseEntity<Void> delete(
            @Parameter(hidden = true)
            @CurrentUser AuthUser authUser,
            @PathVariable("id") Long id
    ) {
        postService.delete(authUser, id);
        return ResponseEntity.noContent().build();
    }

    // 게시글 숨김 처리 (PATCH /api/posts/{id}/hide)
    @PatchMapping("/{id}/hide")
    @Operation(
            summary = "게시글 숨김(HIDE)",
            description = "게시글 상태를 HIDDEN으로 변경합니다. 작성자 본인만 변경 가능합니다."
    )
    public ResponseEntity<BaseResponse<PostResponse>> hide(
            @Parameter(hidden = true)
            @CurrentUser AuthUser authUser,
            @PathVariable("id") Long id
    ) {
        PostResponse response = postService.hide(authUser, id);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }

    // 게시글 복구 처리 (PATCH /api/posts/{id}/activate)
    @PatchMapping("/{id}/activate")
    @Operation(
            summary = "게시글 활성(ACTIVATE)",
            description = "게시글 상태를 ACTIVE로 변경합니다. 작성자 본인만 변경 가능합니다."
    )
    public ResponseEntity<BaseResponse<PostResponse>> activate(
            @Parameter(hidden = true)
            @CurrentUser AuthUser authUser,
            @PathVariable("id") Long id
    ) {
        PostResponse response = postService.activate(authUser, id);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }
}
