package com.hoppy.app.like.repository;

import com.hoppy.app.community.domain.Reply;
import com.hoppy.app.like.domain.MemberReplyLike;
import com.hoppy.app.member.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 태경 2022-07-22
 */
public interface MemberReplyLikeRepository extends JpaRepository<MemberReplyLike, Long> {

    List<MemberReplyLike> findAllByMember(Member member);

    int countAllByReply(Reply reply);
}
