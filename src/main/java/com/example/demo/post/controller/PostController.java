package com.example.demo.post.controller;

import com.example.demo.post.dto.PostCreateRequest;
import com.example.demo.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public Long create(@RequestBody PostCreateRequest request) {
        return postService.createPost(request);
    }


}
