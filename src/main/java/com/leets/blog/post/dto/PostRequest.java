package com.leets.blog.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostRequest {

    @Getter
    @NoArgsConstructor
    @Schema(name = "PostCreateRequest")
    public static class Create {
        @NotBlank(message = "제목은 필수입니다.")
        private String title;

        @NotBlank(message = "내용은 필수입니다.")
        private String content;
    }

    @Getter
    @NoArgsConstructor
    @Schema(name = "PostUpdateRequest")
    public static class Update {
        @NotBlank(message = "수정할 제목을 입력해주세요.")
        private String title;

        @NotBlank(message = "수정할 내용을 입력해주세요.")
        private String content;
    }
}