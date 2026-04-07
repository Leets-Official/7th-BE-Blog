package com.example.blog.domain.user.dto;

import jakarta.validation.constraints.Size;

public record UserUpdateRequest(

    @Size(max = 50, message = "이름은 50자 이하여야 합니다.")
    String username,

    @Size(max = 200, message = "프로필 URL은 200자 이하여야 합니다.")
    String profileUrl

) {}
