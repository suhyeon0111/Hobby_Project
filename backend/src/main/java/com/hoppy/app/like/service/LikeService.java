package com.hoppy.app.like.service;

import com.hoppy.app.community.dto.CountDto;
import com.hoppy.app.member.domain.Member;
import java.util.List;

/**
 * @author 태경 2022-07-22
 */
public interface LikeService {

    public List<Long> getMeetingLikes(long memberId);

    public List<Long> getPostLikes(long memberId);

    public List<Long> getReplyLikes(long memberId);

    public List<Long> getReReplyLikes(long memeberId);

    public int getPostLikeCount(long postId);

    public int getReplyLikeCount(long replyId);

    public int getReReplyLikeCount(long reReplyId);

    public boolean checkMeetingLiked(long memberId, long meetingId);

    public boolean checkPostLiked(long memberId, long postId);
}
