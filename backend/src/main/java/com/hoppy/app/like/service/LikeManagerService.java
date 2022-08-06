package com.hoppy.app.like.service;

import com.hoppy.app.community.dto.CountDto;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.like.domain.LikeManager;
import org.springframework.data.util.Pair;

/**
 * @author 태경 2022-07-22
 */
public interface LikeManagerService {

    public LikeManager createLikeManager();

    public LikeManager getMemberMeetingLikes(Member member);

    public LikeManager getMemberPostLikes(Member member);

    public CountDto getPostsLikeCount(Long postId);
}
