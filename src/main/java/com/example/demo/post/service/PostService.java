package com.example.demo.post.service;

import com.example.demo.media.entity.Media;
import com.example.demo.media.repository.MediaRepository;
import com.example.demo.post.dto.*;
import com.example.demo.post.entity.Post;
import com.example.demo.post.entity.PostBlock;
import com.example.demo.post.repository.PostRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;

    //게시글 생성
    @Transactional
    public Long createPost(PostCreateRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        Post post = new Post();
        post.setUser(user);
        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());
        post.setCreatedAt(LocalDateTime.now());

        int order = 0;

        for (PostBlockDto dto : request.getBlocks()) {

            Media media = null;
            if (dto.getMediaId() != null) {
                media = mediaRepository.findById(dto.getMediaId())
                        .orElseThrow(() -> new IllegalArgumentException("이미지 없음"));
            }

            PostBlock block = PostBlock.builder()
                    .blockType(dto.getBlockType())
                    .sortOrder(order++)
                    .textContent(dto.getTextContent())
                    .media(media)
                    .createdAt(LocalDateTime.now())
                    .build();

            post.addBlock(block);
        }

        return postRepository.save(post).getId();
    }

}
