package com.hoppy.app.like.repository;

import com.hoppy.app.like.domain.MemberMeetingLike;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMeetingLikeRepository extends JpaRepository<MemberMeetingLike, Long> {

    List<MemberMeetingLike> findAllByMember(Member member);
    Optional<MemberMeetingLike> findByMemberAndMeeting(Member member, Meeting meeting);
}
