package com.hoppy.app.member.repository;

import com.hoppy.app.member.domain.MemberMeetingLike;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMeetingLikeRepository extends JpaRepository<MemberMeetingLike, Long> {

    List<MemberMeetingLike> findAllByMemberId(Long memberId);

    Optional<MemberMeetingLike> findMemberMeetingLikeByMemberIdAndMeetingId(Long memberId, Long meetingId);
}
