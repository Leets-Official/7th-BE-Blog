package com.example.week2.report.service;

import com.example.week2.global.response.CustomException;
import com.example.week2.global.response.ErrorCode;
import com.example.week2.post.entity.Post;
import com.example.week2.post.repository.PostRepository;
import com.example.week2.report.entity.Report;
import com.example.week2.report.repository.ReportRepository;
import com.example.week2.user.entity.User;
import com.example.week2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void reportPost(Long postId, Long userId, String reason) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new CustomException(ErrorCode.POST_NOT_FOUND)
                );

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new CustomException(ErrorCode.USER_NOT_FOUND)
                );

        if (reportRepository.existsByPostAndUser(post, user)) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        Report report = Report.builder()
                .post(post)
                .user(user)
                .reason(reason)
                .build();

        reportRepository.save(report);
    }
}
