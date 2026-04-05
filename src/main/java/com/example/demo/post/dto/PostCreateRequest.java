package com.example.demo.post.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class PostCreateRequest {
    private Long userId;
    private String title;
    private String description;
    private List<PostBlockDto> blocks;
}
