package com.hoppy.app.meeting.domain;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.repository.MeetingRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.member.repository.MemberMeetingRepository;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MeetingTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    MemberMeetingRepository memberMeetingRepository;

    @Autowired
    EntityManager em;

    @DisplayName("모임 저장 및 관계 저장 테스트")
    @Test
    @Transactional
    void saveAndSetRelationTest() {

        Member member = Member.builder().build();
        Member member1 = Member.builder().build();

        memberRepository.save(member);
        memberRepository.save(member1);

        Meeting meeting = Meeting.builder()
                .url("none")
                .title("헬스 모임")
                .content("수원 헬창들 모여라")
                .category(Category.HEALTH)
                .memberLimit(10)
                .build();

        meetingRepository.save(meeting);

        MemberMeeting memberMeeting = MemberMeeting.builder()
                .memberId(member.getId())
                .meetingId(meeting.getId())
                .build();

        MemberMeeting memberMeeting1 = MemberMeeting.builder()
                .memberId(member1.getId())
                .meetingId(meeting.getId())
                .build();

        memberMeetingRepository.save(memberMeeting);
        memberMeetingRepository.save(memberMeeting1);

        Assertions.assertThat(em.contains(member)).isTrue();
        Assertions.assertThat(em.contains(member1)).isTrue();
        Assertions.assertThat(em.contains(meeting)).isTrue();
        Assertions.assertThat(em.contains(memberMeeting)).isTrue();
        Assertions.assertThat(em.contains(memberMeeting1)).isTrue();
        em.flush();
        em.clear();

        Optional<Meeting> findMeeting = meetingRepository.findById(meeting.getId());
        assert findMeeting.isPresent();

        Assertions.assertThat(findMeeting.get().getParticipants().size()).isEqualTo(2);

        memberMeetingRepository.deleteAll();
        memberRepository.deleteAll();
        meetingRepository.deleteAll();
    }
}