package com.hoppy.app.meeting.repository;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.member.repository.MemberMeetingRepository;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
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
import org.springframework.data.domain.PageRequest;

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

    @BeforeAll
    void beforeAll() {
        /*
        * 2명의 멤버를 가지는 모임 5개를 생성
        * HEALTH 카테고리 모임 3개
        * LIFE 카테고리 모임 2개
        * */
        for(int i = 0; i < 5; i++) {
            Member member1 = Member.builder().id((long) i + (2 * i)).build();
            Member member2 = Member.builder().id(member1.getId() + 1).build();

            memberRepository.save(member1);
            memberRepository.save(member2);

            Category meetingCategory;
            if(i % 2 == 0) meetingCategory = Category.HEALTH;
            else meetingCategory = Category.LIFE;

            Meeting meeting = meetingRepository.save(Meeting.builder()
                    .ownerId(member1.getId())
                    .url("none")
                    .title(i + "번 모임")
                    .content(i + "번 모임 회원들 모여라")
                    .category(meetingCategory)
                    .memberLimit(10)
                    .build());

            memberMeetingRepository.save(MemberMeeting.builder()
                    .meetingId(meeting.getId())
                    .memberId(member1.getId())
                    .build());

            memberMeetingRepository.save(MemberMeeting.builder()
                    .meetingId(meeting.getId())
                    .memberId(member2.getId())
                    .build());
        }
    }

    @Transactional
    @AfterAll
    void after() {
        memberMeetingRepository.deleteAll();
        memberRepository.deleteAll();
        meetingRepository.deleteAll();
    }

    @DisplayName("infiniteScrollPaging 테스트")
    @Transactional
    @Test
    void infiniteScrollPagingTest() {

        List<Meeting> result = meetingRepository.infiniteScrollPagingMeeting(Category.HEALTH, 0L, PageRequest.of(0, 100));

        System.out.println("조회된 모임 중 마지막 모임 id = " + result.get(result.size() - 1).getId());
        for (Meeting m : result) {
            Assertions.assertThat(m.getParticipants().size()).isEqualTo(2);

            /*
             * 참여자 목록을 조회할 때 참여자 수 만큼 쿼리가 나가는 효율 문제가 있음
             * 참여자 목록을 한 번에 조회하자.
             *
             * 먼저 getParticipants를 가공해서 모든 참여자의 id를 List로 받아온다.
             * 그 다음 WHERE IN 을 사용해서 List에 id들을 한 번에 조회하자.
            * */

            List<Long> membersIdList = m.getParticipants()
                    .stream()
                    .map(MemberMeeting::getMemberId)
                    .collect(Collectors.toList());

            List<Member> memberList = memberRepository.infiniteScrollPagingMember(membersIdList, 0L, PageRequest.of(0, 100));

            System.out.println("참여자 목록");
            for (Member member : memberList) {
                System.out.println(member);
            }
        }
    }
}