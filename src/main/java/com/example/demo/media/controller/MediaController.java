package com.example.demo.media.controller;

import com.example.demo.media.dto.MediaResponse;
import com.example.demo.media.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/media")
public class MediaController {

    private final MediaService mediaService;

    @PostMapping("/upload")
    public MediaResponse upload(@RequestParam("file") MultipartFile file) {
        return mediaService.upload(file);
    }
}
