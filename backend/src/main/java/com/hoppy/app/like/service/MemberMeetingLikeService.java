package com.hoppy.app.like.service;

import com.hoppy.app.like.domain.LikeManager;
import com.hoppy.app.like.domain.MemberMeetingLike;
import java.util.Optional;

/**
 * @author 태경 2022-07-22
 */
public interface MemberMeetingLikeService {

    public Optional<MemberMeetingLike> findMemberMeetingLikeByLikeManagerAndMeetingId(
            LikeManager likeManager, Long meetingId);
}
