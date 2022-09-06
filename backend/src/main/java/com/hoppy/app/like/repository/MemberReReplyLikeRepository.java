package com.hoppy.app.like.repository;

import com.hoppy.app.community.domain.ReReply;
import com.hoppy.app.like.domain.MemberReReplyLike;
import com.hoppy.app.like.domain.MemberReplyLike;
import com.hoppy.app.member.domain.Member;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author 태경 2022-08-10
 */
public interface MemberReReplyLikeRepository extends JpaRepository<MemberReReplyLike, Long> {

    List<MemberReReplyLike> findAllByMember(Member member);

    int countAllByReReply(ReReply reReply);

    @Query("select m from MemberReReplyLike m " +
            "where m.member.id = :memberId and m.reReply.id = :reReplyId")
    Optional<MemberReReplyLike> findByMemberIdAndReplyId(
            @Param("memberId") Long memberId,
            @Param("reReplyId") Long reReplyId
    );
}
