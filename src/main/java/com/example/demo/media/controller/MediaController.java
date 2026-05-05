package com.example.demo.media.controller;

import com.example.demo.media.dto.MediaResponse;
import com.example.demo.media.service.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Media", description = "미디어 파일 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/media")
public class MediaController {

    private final MediaService mediaService;

    @Operation(
            summary = "미디어 파일 업로드 API",
            description = "이미지 등의 미디어 파일을 업로드하고, 저장된 파일 정보를 반환합니다."
    )
    @PostMapping("/upload")
    public MediaResponse upload(
            @Parameter(description = "업로드할 파일", required = true)
            @RequestParam("file") MultipartFile file) {

        return mediaService.upload(file);
    }
}
