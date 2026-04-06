package com.example.leets7th.domain.post.controller;

import com.example.leets7th.domain.post.dto.res.PostResponseDTO;
import com.example.leets7th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Tag(name = "Post", description = "게시글 관련 API")
public interface PostControllerDocs {
    @Operation(
            summary = "게시글 목록 조회 api",
            description = "게시글의 목록을 반환합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH401_1", description = "인증이 필요합니다.")
    })
    @GetMapping("/api/posts")
    ApiResponse<List<PostResponseDTO.PostListResDTO>> getPostList(
            @RequestHeader @Valid Long userId
    );

}
