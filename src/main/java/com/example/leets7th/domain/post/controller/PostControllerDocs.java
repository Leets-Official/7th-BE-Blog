package com.example.leets7th.domain.post.controller;

import com.example.leets7th.domain.post.dto.req.PostRequestDTO;
import com.example.leets7th.domain.post.dto.res.PostResponseDTO;
import com.example.leets7th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post", description = "게시글 관련 API")
public interface PostControllerDocs {
    @Operation(
            summary = "게시글 목록 조회 api",
            description = "게시글의 목록을 반환합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST200_1", description = "게시글 리스트 조회에 성공했습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH401_1", description = "인증이 필요합니다.")
    })
    @GetMapping("/api/posts")
    ApiResponse<List<PostResponseDTO.PostListResDTO>> getPostList(
            @RequestHeader @Valid Long userId
    );

    @Operation(
            summary = "게시글 상세 조회 api",
            description = "게시글의 상세 내용을 반환합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST200_1", description = "게시글 상세 조회에 성공했습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH401_1", description = "인증이 필요합니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST404_1", description = "해당 게시글이 존재하지 않습니다."),
    })
    @GetMapping("/api/posts/{postId}")
    ApiResponse<PostResponseDTO.PostDetailResDTO> getPostDetail(
            @PathVariable Long postId,
            @RequestHeader @Valid Long userId
    );

    @Operation(
            summary = "게시글 작성 api",
            description = "게시글을 작성합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST201_3", description = "게시글 작성에 성공하였습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH401_1", description = "인증이 필요합니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST400_1", description = "제목을 입력해주세요."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST400_2", description = "제목은 최대 255자까지 가능합니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST400_3", description = "내용을 입력해주세요."),
    })
    @PostMapping("/api/posts")
    ApiResponse<PostResponseDTO.CreatePostResDTO> createPost(
            @RequestHeader @Valid Long userId,
            @Valid @RequestBody PostRequestDTO.PostReqDTO req
    );

}
