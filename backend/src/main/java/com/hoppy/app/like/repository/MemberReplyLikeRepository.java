package com.hoppy.app.like.repository;

import com.hoppy.app.community.domain.Reply;
import com.hoppy.app.like.domain.MemberPostLike;
import com.hoppy.app.like.domain.MemberReplyLike;
import com.hoppy.app.member.domain.Member;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author 태경 2022-07-22
 */
public interface MemberReplyLikeRepository extends JpaRepository<MemberReplyLike, Long> {

    List<MemberReplyLike> findAllByMember(Member member);

    int countAllByReply(Reply reply);

    @Query("select m from MemberReplyLike m " +
            "where m.member.id = :memberId and m.reply.id = :replyId")
    Optional<MemberReplyLike> findByMemberIdAndReplyId(
            @Param("memberId") Long memberId,
            @Param("replyId") Long replyId
    );
}
