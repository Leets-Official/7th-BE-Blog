package com.leets.blog.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostRequest {

    @Getter
    @NoArgsConstructor
    public static class Create {
        private String title;
        private String content;
    }

    @Getter
    @NoArgsConstructor
    public static class Update {
        private String title;
        private String content;
    }
}