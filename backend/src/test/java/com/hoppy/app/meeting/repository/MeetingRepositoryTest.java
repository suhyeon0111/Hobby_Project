package com.hoppy.app.meeting.repository;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.member.repository.MemberMeetingRepository;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class MeetingRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    MemberMeetingRepository memberMeetingRepository;

    @Autowired
    EntityManager em;



    @Transactional
    @BeforeAll
    void beforeAll() {
        Random random = new Random();
        /*
        * 2명의 멤버를 가지는 모임 5개를 생성
        * HEALTH 카테고리 모임 3개
        * LIFE 카테고리 모임 2개
        * */
        for(int i = 0; i < 5; i++) {
            Member member1 = Member.builder().id((long) i * random.nextInt(100)).build();
            Member member2 = Member.builder().id((long) i * random.nextInt(100)).build();

            memberRepository.save(member1);
            memberRepository.save(member2);

            Category meetingCategory;
            if(i % 2 == 0) meetingCategory = Category.HEALTH;
            else meetingCategory = Category.LIFE;

            Meeting meeting = Meeting.builder()
                    .url("none")
                    .title(i + "번 모임")
                    .content(i + "번 모임 회원들 모여라")
                    .category(meetingCategory)
                    .memberLimit(10)
                    .build();

            meetingRepository.save(meeting);

            MemberMeeting memberMeeting1 = MemberMeeting.builder()
                    .meeting(meeting)
                    .member(member1)
                    .build();

            MemberMeeting memberMeeting2 = MemberMeeting.builder()
                    .meeting(meeting)
                    .member(member2)
                    .build();

            memberMeetingRepository.save(memberMeeting1);
            memberMeetingRepository.save(memberMeeting2);
        }
    }

    @Transactional
    @AfterAll
    void after() {
        memberMeetingRepository.deleteAll();
        memberRepository.deleteAll();
        meetingRepository.deleteAll();
    }

    @DisplayName("HEALTH, LIFE 카테고리 모임을 조회하고 멤버의 수를 확인하는 테스트")
    @Transactional
    @Test
    void findAllMeetingUsingFetchTest() {

        List<Meeting> healthMeetingList = meetingRepository.findAllMeetingByCategoryUsingFetch(Category.HEALTH);
        Assertions.assertThat(healthMeetingList.size()).isEqualTo(3);

        for(Meeting m : healthMeetingList) {
            Assertions.assertThat(m.getParticipants().size()).isEqualTo(2);
        }

        List<Meeting> LifeMeetingList = meetingRepository.findAllMeetingByCategoryUsingFetch(Category.LIFE);
        Assertions.assertThat(LifeMeetingList.size()).isEqualTo(2);

        for(Meeting m : LifeMeetingList) {
            Assertions.assertThat(m.getParticipants().size()).isEqualTo(2);
        }
    }
}