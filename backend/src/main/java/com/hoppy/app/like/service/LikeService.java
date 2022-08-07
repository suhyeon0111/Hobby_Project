package com.hoppy.app.like.service;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.dto.CountDto;
import com.hoppy.app.like.domain.MemberMeetingLike;
import com.hoppy.app.like.domain.MemberPostLike;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.like.domain.LikeManager;
import java.util.List;
import java.util.Set;
import org.springframework.data.util.Pair;

/**
 * @author 태경 2022-07-22
 */
public interface LikeService {

    public List<Long> getMeetingLikes(Member member);

    public List<Long> getPostLikes(Member member);

    public CountDto getPostLikeCount(Long postId);

    public Boolean checkMeetingLiked(Member member, Long meetingId);

    public Boolean checkPostLiked(Member member, Long postId);
}
