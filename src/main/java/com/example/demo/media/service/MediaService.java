package com.example.demo.media.service;

import com.example.demo.media.dto.MediaResponse;
import com.example.demo.media.entity.Media;
import com.example.demo.media.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;
    private final String uploadDir = "C:/upload/";

    public MediaResponse upload(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + fileName);
            file.transferTo(dest);

            Media media = Media.builder()
                    .url("/images/" + fileName)
                    .originalName(file.getOriginalFilename())
                    .mimeType(file.getContentType())
                    .size(file.getSize())
                    .createdAt(LocalDateTime.now())
                    .build();

            Media saved = mediaRepository.save(media);

            return MediaResponse.builder()
                    .id(saved.getId())
                    .url(saved.getUrl())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("파일 업로드 실패");
        }
    }
}
