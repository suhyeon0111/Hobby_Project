package com.hoppy.app.like.repository;

import com.hoppy.app.community.domain.ReReply;
import com.hoppy.app.like.domain.MemberReReplyLike;
import com.hoppy.app.member.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 태경 2022-08-10
 */
public interface MemberReReplyLikeRepository extends JpaRepository<MemberReReplyLike, Long> {

    List<MemberReReplyLike> findAllByMember(Member member);

    int countAllByReReply(ReReply reReply);
}
