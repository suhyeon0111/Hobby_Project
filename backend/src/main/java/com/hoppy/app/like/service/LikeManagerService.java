package com.hoppy.app.like.service;

import com.hoppy.app.community.dto.CountDto;
import com.hoppy.app.like.domain.MemberMeetingLike;
import com.hoppy.app.like.domain.MemberPostLike;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.like.domain.LikeManager;
import java.util.Set;
import org.springframework.data.util.Pair;

/**
 * @author 태경 2022-07-22
 */
public interface LikeManagerService {

    public LikeManager createLikeManager();

    public Set<MemberMeetingLike> getMeetingLikes(Member member);

    public Set<MemberPostLike> getPostLikes(Member member);

    public CountDto getLikeCount(Long postId);
}
