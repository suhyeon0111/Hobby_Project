package com.hoppy.app.member.repository;

import com.hoppy.app.member.domain.MemberMeeting;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMeetingRepository extends JpaRepository<MemberMeeting, Long> {

    void deleteMemberMeetingByMeetingIdAndMemberId(Long meetingId, Long memberId);

    List<MemberMeeting> findALlByMeetingId(Long meetingId);
}
