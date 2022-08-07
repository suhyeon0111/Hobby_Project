package com.hoppy.app.like.repository;

import com.hoppy.app.like.domain.MemberMeetingLike;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMeetingLikeRepository extends JpaRepository<MemberMeetingLike, Long> {

    Optional<MemberMeetingLike> findByMemberIdAndMeetingId(Long memberId, Long meetingId);
    List<MemberMeetingLike> findAllByMemberIdAndMeetingId(Long memberId, Long meetingId);
    List<MemberMeetingLike> findAllByMemberId(Long memberId);
    List<MemberMeetingLike> findAllByMeetingId(Long meetingId);
}
