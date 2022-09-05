package com.hoppy.app.like.repository;

import com.hoppy.app.like.domain.MemberStoryReReplyLike;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.story.domain.story.StoryReReply;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberStoryReReplyLikeRepository extends JpaRepository<StoryReReply, Long> {

    List<MemberStoryReReplyLike> findAllByMember(Member member);

    @Query("select s from MemberStoryReReplyLike s where s.member.id = :memberId and s.reReply.id = :reReplyId")
    Optional<MemberStoryReReplyLike> findByMemberIdAndReplyId(
            @Param("memberId") Long memberId,
            @Param("reReplyId") Long reReplyId
    );
}
