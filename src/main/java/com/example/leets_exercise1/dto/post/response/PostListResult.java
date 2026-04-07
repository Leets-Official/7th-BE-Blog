package com.example.leets_exercise1.dto.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostListResult {
    private List<PostSummaryResponse> posts;
}