package com.hoppy.app.member.service;

import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.repository.MeetingRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.member.repository.MemberMeetingRepository;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberMeetingServiceImpl implements MemberMeetingService {

    private final MemberRepository memberRepository;
    private final MemberMeetingRepository meetingRepository;

    @Override
    public void joinToMeetingById(Long memberId, Long meetingId) {
        System.out.println("memberId = " + memberId);
        System.out.println("meetingId = " + meetingId);
        System.out.println("memberId.getClass() = " + memberId.getClass());
        System.out.println("memberId.getClass() = " + memberId.getClass());

        meetingRepository.save(MemberMeeting.builder().meetingId(meetingId).memberId(memberId).build());
    }
}
