package com.hoppy.app.like.service;

import com.hoppy.app.community.dto.CountDto;
import com.hoppy.app.member.domain.Member;
import java.util.List;

/**
 * @author 태경 2022-07-22
 */
public interface LikeService {

    public List<Long> getMeetingLikes(Long memberId);

    public List<Long> getPostLikes(Long memberId);

    public int getPostLikeCount(Long postId);

    public Boolean checkMeetingLiked(Long memberId, Long meetingId);

    public Boolean checkPostLiked(Long memberId, Long postId);
}
