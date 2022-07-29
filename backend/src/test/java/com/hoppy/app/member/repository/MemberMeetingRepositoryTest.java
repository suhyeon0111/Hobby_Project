package com.hoppy.app.member.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.repository.MeetingRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberMeeting;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @author 태경 2022-07-29
 */
@DataJpaTest
@TestInstance(Lifecycle.PER_METHOD)
class MemberMeetingRepositoryTest {

    @Autowired
    MemberMeetingRepository memberMeetingRepository;

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void deleteMemberMeetingByMeetingIdAndMemberId() {

        //given
        Member member = memberRepository.save(Member.builder().id(0L).build());
        Meeting meeting = meetingRepository.save(Meeting.builder()
                .ownerId(0L)
                .title("test")
                .content("test")
                .memberLimit(15)
                .category(Category.HEALTH)
                .build());

        MemberMeeting memberMeeting = memberMeetingRepository.save(
                MemberMeeting.builder()
                        .memberId(member.getId())
                        .meetingId(meeting.getId())
                        .build()
        );

        //when
        memberMeetingRepository.deleteMemberMeetingByMeetingIdAndMemberId(meeting.getId(), member.getId());

        //then
        Optional<Meeting> meetingOptional = meetingRepository.findById(meeting.getId());
        assert meetingOptional.isPresent();

        meeting = meetingOptional.get();
        assertThat(meeting.getParticipants().size()).isEqualTo(0);
    }
}