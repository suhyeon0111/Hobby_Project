package com.hoppy.app.like.service;

import com.hoppy.app.community.dto.CountDto;
import com.hoppy.app.like.domain.MemberMeetingLike;
import com.hoppy.app.like.domain.MemberPostLike;
import com.hoppy.app.like.repository.MemberMeetingLikeRepository;
import com.hoppy.app.like.repository.MemberPostLikeRepository;
import com.hoppy.app.member.domain.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 태경 2022-07-22
 */
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final MemberPostLikeRepository memberPostLikeRepository;
    private final MemberMeetingLikeRepository memberMeetingLikeRepository;

    @Override
    public List<Long> getMeetingLikes(Long memberId) {
        return memberMeetingLikeRepository.findAllByMemberId(memberId).stream()
                .map(MemberMeetingLike::getMeetingId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getPostLikes(Long memberId) {
        return memberPostLikeRepository.findAllByMemberId(memberId).stream()
                .map(MemberPostLike::getPostId)
                .collect(Collectors.toList());
    }

    @Override
    public int getPostLikeCount(Long postId) {
        return memberPostLikeRepository.findAllByPostId(postId).size();
    }

    @Override
    public Boolean checkMeetingLiked(Long memberId, Long meetingId) {
        return memberMeetingLikeRepository.findByMemberIdAndMeetingId(memberId, meetingId).isPresent();
    }

    @Override
    public Boolean checkPostLiked(Long memberId, Long postId) {
        return memberPostLikeRepository.findByMemberIdAndPostId(memberId, postId).isPresent();
    }
}
