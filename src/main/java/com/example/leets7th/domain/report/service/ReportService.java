package com.example.leets7th.domain.report.service;

import com.example.leets7th.domain.post.entity.Post;
import com.example.leets7th.domain.post.exception.PostException;
import com.example.leets7th.domain.post.exception.code.PostErrorCode;
import com.example.leets7th.domain.post.repository.PostRepository;
import com.example.leets7th.domain.report.dto.req.ReportReqDTO;
import com.example.leets7th.domain.report.entity.Report;
import com.example.leets7th.domain.report.repository.ReportRepository;
import com.example.leets7th.domain.user.entity.User;
import com.example.leets7th.domain.user.repository.UserRepository;
import com.example.leets7th.global.apiPayload.code.GeneralErrorCode;
import com.example.leets7th.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReportRepository reportRepository;

    @Transactional
    public void reportPost(ReportReqDTO.CreateReportDTO request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.UNAUTHORIZED));

        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        Report newReport = Report.builder()
                .user(user)
                .post(post)
                .reportType(request.reportType())
                .content(request.content())
                .build();

        reportRepository.save(newReport);
    }
}
