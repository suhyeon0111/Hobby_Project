package com.hoppy.app.like.service;

import com.hoppy.app.community.dto.CountDto;
import com.hoppy.app.like.domain.MemberMeetingLike;
import com.hoppy.app.like.domain.MemberPostLike;
import com.hoppy.app.like.repository.MemberMeetingLikeRepository;
import com.hoppy.app.like.repository.MemberPostLikeRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.like.domain.LikeManager;
import com.hoppy.app.like.repository.LikeManagerRepository;
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

    private final LikeManagerRepository likeManagerRepository;
    private final MemberPostLikeRepository memberPostLikeRepository;
    private final MemberMeetingLikeRepository memberMeetingLikeRepository;

    private void createLikeManager(Member member) {
        likeManagerRepository.save(
                LikeManager.builder()
                        .member(member)
                        .build()
        );
    }

    @Override
    public List<Long> getMeetingLikes(Member member) {
        Optional<LikeManager> optionalLikeManager = likeManagerRepository.findLikeManagerWithMeetingLikesByMember(member);

        if(optionalLikeManager.isEmpty()) {
            createLikeManager(member);
            return new ArrayList<>();
        }
        return optionalLikeManager.get().getMeetingLikes().stream()
                .map(MemberMeetingLike::getMeetingId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getPostLikes(Member member) {
        Optional<LikeManager> optionalLikeManager = likeManagerRepository.findLikeManagerWithPostLikesByMember(member);

        if(optionalLikeManager.isEmpty()) {
            createLikeManager(member);
            return new ArrayList<>();
        }
        return optionalLikeManager.get().getPostLikes().stream()
                .map(MemberPostLike::getPostId)
                .collect(Collectors.toList());
    }

    @Override
    public CountDto getPostLikeCount(Long postId) {
        return CountDto.of(postId, memberPostLikeRepository.findAllByPostId(postId).size());
    }

    @Override
    public Boolean checkMeetingLiked(Member member, Long meetingId) {
        return memberMeetingLikeRepository.findMemberMeetingLikeByLikeManagerAndMeetingId(member.getLikeManager(), meetingId).isPresent();
    }

    @Override
    public Boolean checkPostLiked(Member member, Long postId) {
        return memberPostLikeRepository.findMemberPostLikeByLikeManagerAndPostId(member.getLikeManager(), postId).isPresent();
    }
}
