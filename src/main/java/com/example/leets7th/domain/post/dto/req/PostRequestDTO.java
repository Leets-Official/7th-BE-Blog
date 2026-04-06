package com.example.leets7th.domain.post.dto.req;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public class PostRequestDTO {
    @Builder
    public record PostReqDTO (
            @NotBlank(message = "POST400_1|제목을 입력해주세요.")
            @Size(max = 255, message = "POST400_2|제목은 최대 255자까지 가능합니다.")
            String title,

            @NotBlank(message = "POST400_3|내용을 입력해주세요.")
            String content
    ){}
}
