package com.example.demo.post.dto;

import com.example.demo.post.entity.BlockType;
import lombok.Getter;

@Getter
public class PostBlockDto {
    private BlockType blockType;
    private String textContent;
    private Long mediaId;
}
