package com.hoppy.app.like.service;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.dto.CountDto;
import com.hoppy.app.like.domain.MemberMeetingLike;
import com.hoppy.app.like.domain.MemberPostLike;
import com.hoppy.app.like.domain.MemberReReplyLike;
import com.hoppy.app.like.domain.MemberReplyLike;
import com.hoppy.app.like.repository.MemberMeetingLikeRepository;
import com.hoppy.app.like.repository.MemberPostLikeRepository;
import com.hoppy.app.like.repository.MemberReReplyLikeRepository;
import com.hoppy.app.like.repository.MemberReplyLikeRepository;
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

    private final MemberReplyLikeRepository memberReplyLikeRepository;

    private final MemberReReplyLikeRepository memberReReplyLikeRepository;

    @Override
    public List<Long> getMeetingLikes(long memberId) {
        return memberMeetingLikeRepository.findAllByMemberId(memberId).stream()
                .map(M -> M.getMeeting().getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getPostLikes(long memberId) {
        return memberPostLikeRepository.findAllByMemberId(memberId).stream()
                .map(M -> M.getPost().getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getReplyLikes(long memberId) {
        return memberReplyLikeRepository.findAllByMemberId(memberId).stream()
                .map(M -> M.getReply().getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getReReplyLikes(long memberId) {
        return memberReReplyLikeRepository.findAllByMemberId(memberId).stream()
                .map(M -> M.getReReply().getId())
                .collect(Collectors.toList());
    }

    @Override
    public int getPostLikeCount(long postId) {
        return memberPostLikeRepository.countAllByPostId(postId);
    }

    @Override
    public int getReplyLikeCount(long replyId) {
        return memberReplyLikeRepository.countAllByReplyId(replyId);
    }

    @Override
    public int getReReplyLikeCount(long reReplyId) {
        return memberReReplyLikeRepository.countAllByReReplyId(reReplyId);
    }

    @Override
    public boolean checkMeetingLiked(long memberId, long meetingId) {
        return memberMeetingLikeRepository.findByMemberIdAndMeetingId(memberId, meetingId).isPresent();
    }

    @Override
    public boolean checkPostLiked(long memberId, long postId) {
        return memberPostLikeRepository.findByMemberIdAndPostId(memberId, postId).isPresent();
    }
}
