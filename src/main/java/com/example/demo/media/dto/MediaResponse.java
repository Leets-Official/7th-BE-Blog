package com.example.demo.media.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MediaResponse {
    private Long id;
    private String url;
}
