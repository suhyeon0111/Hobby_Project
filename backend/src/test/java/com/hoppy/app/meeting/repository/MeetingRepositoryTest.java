package com.hoppy.app.meeting.repository;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.member.repository.MemberMeetingRepository;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.List;
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
import org.springframework.data.domain.Page;
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

    @Transactional
    @BeforeAll
    void beforeAll() {
        /*
        * 2명의 멤버를 가지는 모임 5개를 생성
        * HEALTH 카테고리 모임 3개
        * LIFE 카테고리 모임 2개
        * */
        for(int i = 0; i < 20; i++) {
            Member member1 = memberRepository.save(Member
                    .builder()
                    .username("test" + i)
                    .build());

            Member member2 = memberRepository.save(Member
                    .builder()
                    .username("test" + (i + 1))
                    .build());

            Category meetingCategory;
            if(i % 2 == 0) meetingCategory = Category.HEALTH;
            else meetingCategory = Category.LIFE;

            Meeting meeting = meetingRepository.save(Meeting.builder()
                    .url("none")
                    .title(i + "번 모임")
                    .content(i + "번 모임 회원들 모여라")
                    .category(meetingCategory)
                    .memberLimit(10)
                    .build());

            MemberMeeting memberMeeting1 = memberMeetingRepository.save(MemberMeeting.builder()
                    .meetingId(meeting.getId())
                    .memberId(member1.getId())
                    .build());

            MemberMeeting memberMeeting2 = memberMeetingRepository.save(MemberMeeting.builder()
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

    @DisplayName("HEALTH, LIFE 카테고리 모임을 조회하고 멤버의 수를 확인하는 테스트")
    @Transactional
    @Test
    void findAllMeetingUsingFetchTest() {

        List<Meeting> healthMeetingList = meetingRepository.findAllMeetingByCategoryUsingFetch(Category.HEALTH);
        Assertions.assertThat(healthMeetingList.size()).isEqualTo(3);

        System.out.println("모임 참가자 수 확인하기");

        for(Meeting m : healthMeetingList) {
            Assertions.assertThat(m.getParticipants().size()).isEqualTo(2);
        }

        List<Meeting> LifeMeetingList = meetingRepository.findAllMeetingByCategoryUsingFetch(Category.LIFE);
        Assertions.assertThat(LifeMeetingList.size()).isEqualTo(2);

        for(Meeting m : LifeMeetingList) {
            Assertions.assertThat(m.getParticipants().size()).isEqualTo(2);
        }
    }

    @DisplayName("No Offset 페이지네이션 테스트")
    @Transactional
    @Test
    void paginationTest() {

        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<Meeting> result = meetingRepository.findAllMeetingByCategoryOrderByIdDesc(Category.HEALTH, pageRequest);

        Assertions.assertThat(result.getContent().size()).isEqualTo(2);
    }

    @DisplayName("infiniteScrollPaging 테스트")
    @Transactional
    @Test
    void infiniteScrollPagingTest() {

        List<Meeting> result = meetingRepository.infiniteScrollPaging(Category.HEALTH, PageRequest.of(0, 100), 0L);

        System.out.println("==========result==========");
        for (Meeting m : result) {
            System.out.println(m + ", 참여자 " + m.getParticipants().size() + "명");

            /*
             * 참여자 목록을 조회할 때 참여자 수 만큼 쿼리가 나가는 효율 문제가 있음
             * 참여자 목록을 한 번에 조회하자.
             *
             * 먼저 getParticipants로 모든 참여자의 id를 List로 받아온다.
             * 그 다음 WHERE IN 을 사용해서 List에 id들을 모두 한 번에 조회하자.
            * */
            System.out.println("참여자 id = " + m.getParticipants()
                    .stream()
                    .map(MemberMeeting::getMemberId)
                    .collect(Collectors.toList())
            );

//            System.out.println("참여자 목록");
//            for (MemberMeeting meet : m.getParticipants()) {
//                System.out.println(meet.getMember().getUsername());

//            }
        }
    }
}