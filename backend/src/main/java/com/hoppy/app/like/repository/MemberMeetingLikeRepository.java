package com.hoppy.app.like.repository;

import com.hoppy.app.like.domain.MemberMeetingLike;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMeetingLikeRepository extends JpaRepository<MemberMeetingLike, Long> {

    List<MemberMeetingLike> findAllByMember(Member member);

    @Query("select m from MemberMeetingLike m " +
            "where m.member.id = :memberId and m.meeting.id = :meetingId")
    Optional<MemberMeetingLike> findByMemberIdAndMeetingId(@Param("memberId") Long memberId, @Param("meetingId") Long meetingId);

    @Modifying
    @Query("delete from MemberMeetingLike m " +
            "where m.member.id = :memberId and m.meeting.id = :meetingId")
    void deleteByMemberIdAndMeetingId(
            @Param("memberId") Long memberId,
            @Param("meetingId") Long meetingId
    );
}
