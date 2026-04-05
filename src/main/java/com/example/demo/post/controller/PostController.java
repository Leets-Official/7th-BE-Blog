package com.example.demo.post.controller;

import com.example.demo.global.exception.ApiResponse;
import com.example.demo.post.dto.PostCreateRequest;
import com.example.demo.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ApiResponse<Long> create(@RequestBody @Valid PostCreateRequest request) {
        return ApiResponse.<Long>builder()
                .success(true)
                .data(postService.createPost(request))
                .build();
    }


}
