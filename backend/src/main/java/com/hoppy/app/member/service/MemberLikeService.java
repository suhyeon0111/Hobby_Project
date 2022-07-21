package com.hoppy.app.member.service;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberLike;

/**
 * @author 태경 2022-07-22
 */
public interface MemberLikeService {

    MemberLike createMemberLike();

    MemberLike getMemberLikeWithMeetingLikes(Member member);
}
