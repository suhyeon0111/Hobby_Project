package com.hoppy.app.like.service;

import com.hoppy.app.like.domain.LikeManager;
import com.hoppy.app.like.domain.MemberMeetingLike;
import com.hoppy.app.like.repository.MemberMeetingLikeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 태경 2022-07-22
 */
@Service
@RequiredArgsConstructor
public class MemberMeetingLikeServiceImpl implements MemberMeetingLikeService {

    private final MemberMeetingLikeRepository memberMeetingLikeRepository;

    @Override
    public Optional<MemberMeetingLike> findMemberMeetingLikeByLikeManagerAndMeetingId(
            LikeManager likeManager, Long meetingId) {
        return memberMeetingLikeRepository.findMemberMeetingLikeByLikeManagerAndMeetingId(
                likeManager, meetingId);
    }
}
