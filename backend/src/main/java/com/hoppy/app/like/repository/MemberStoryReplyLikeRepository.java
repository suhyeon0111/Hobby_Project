package com.hoppy.app.like.repository;

import com.hoppy.app.like.domain.MemberPostLike;
import com.hoppy.app.like.domain.MemberReplyLike;
import com.hoppy.app.like.domain.MemberStoryReplyLike;
import com.hoppy.app.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberStoryReplyLikeRepository {

    List<MemberStoryReplyLike> findAllByMember(Member member);

    @Query("select s from MemberStoryReplyLike s where s.member.id = :memberId and s.reply.id = :replyId")
    Optional<MemberStoryReplyLike> findByMemberIdAndReplyId(
            @Param("memberId") Long memberId,
            @Param("replyId") Long replyId
    );
}
