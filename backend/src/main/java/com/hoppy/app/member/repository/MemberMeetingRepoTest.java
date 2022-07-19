package com.hoppy.app.member.repository;

import com.hoppy.app.meeting.repository.MeetingRepository;
import com.hoppy.app.member.domain.MemberMeeting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberMeetingRepoTest {

    private final MemberMeetingRepository meetingRepository;

    public void meetingRepoTest(Long id) {
        MemberMeeting meeting = MemberMeeting.builder().meetingId(12L).memberId(id).build();
        System.out.println("meeting.getMeetingId() = " + meeting.getMeetingId());
        System.out.println("meeting.getMemberId() = " + meeting.getMemberId());
        meetingRepository.save(meeting);
    }
}
