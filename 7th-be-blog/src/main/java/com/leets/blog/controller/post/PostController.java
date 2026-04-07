package com.leets.blog.controller.post;

import com.leets.blog.common.ApiResponse;
import com.leets.blog.dto.post.PostRequest;
import com.leets.blog.dto.post.PostResponse;
import com.leets.blog.service.post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 1. 게시글 목록 조회 (PDF 설계: GET /posts)
    @GetMapping
    public ApiResponse<List<PostResponse>> getPostList() {
        return ApiResponse.onSuccess(postService.getPostList());
    }

    // 2. 게시글 상세 조회 (PDF 설계: GET /posts/{postId})
    @GetMapping("/{postId}")
    public ApiResponse<PostResponse> getPostDetail(@PathVariable Long postId) {
        return ApiResponse.onSuccess(postService.getPostDetail(postId));
    }

    // 3. 게시글 작성 (PDF 설계: POST /posts, @Valid 적용)
    @PostMapping
    public ApiResponse<PostResponse> createPost(@RequestBody @Valid PostRequest request) {
        return ApiResponse.onSuccess(postService.createPost(request));
    }

    // 4. 게시글 삭제 (PDF 설계: DELETE /posts/{postId})
    @DeleteMapping("/{postId}")
    public ApiResponse<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ApiResponse.onSuccess("게시글이 성공적으로 삭제되었습니다.");
    }
}
