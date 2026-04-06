package com.example.demo.post.dto;

import com.example.demo.post.entity.BlockType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostBlockResponse {

    private BlockType blockType;
    private String textContent;
    private String imageUrl;
}
