package com.hoppy.app.like.repository;

import com.hoppy.app.like.domain.MemberMeetingLike;
import com.hoppy.app.like.domain.MemberReplyLike;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 태경 2022-07-22
 */
public interface MemberReplyLikeRepository extends JpaRepository<MemberReplyLike, Long> {

    List<MemberReplyLike> findAllByMemberId(Long memberId);

    int countAllByReplyId(Long replyId);
}
