package com.hoppy.app.like.service;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.like.domain.LikeManager;

/**
 * @author 태경 2022-07-22
 */
public interface LikeManagerService {

    LikeManager createMemberLike();

    LikeManager getMemberLikeWithMeetingLikes(Member member);
}
