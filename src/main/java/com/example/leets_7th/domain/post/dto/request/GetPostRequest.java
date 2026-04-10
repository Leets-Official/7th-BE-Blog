package com.example.leets_7th.domain.post.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public record GetPostRequest(

        @NotNull(message = "userId는 필수입니다")
        Long userId,

        @Min(value = 1, message = "page는 1 이상이어야 합니다")
        int page,

        @Min(value = 1, message = "size는 1 이상이어야 합니다")
        @Max(value = 50, message = "size는 50 이하여야 합니다")
        int size
) {
        public Pageable toPageable() {
                return PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        }
}
