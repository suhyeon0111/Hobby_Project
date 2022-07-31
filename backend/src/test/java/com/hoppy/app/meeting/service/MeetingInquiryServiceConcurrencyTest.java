package com.hoppy.app.meeting.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.repository.MeetingRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.member.repository.MemberMeetingRepository;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author 태경 2022-07-30
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MeetingInquiryServiceConcurrencyTest {

    @Autowired
    MeetingInquiryServiceImpl meetingInquiryService;

    @Autowired
    MeetingManageService meetingManageService;

    @Autowired
    MemberMeetingRepository memberMeetingRepository;

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    MemberRepository memberRepository;

    Logger log = (Logger) LoggerFactory.getLogger(MeetingInquiryServiceConcurrencyTest.class);

    @AfterEach
    void after() {
        log.info("[afterEach 메서드 수행]");

        memberRepository.deleteAll();
        meetingRepository.deleteAll();
        memberMeetingRepository.deleteAll();
    }

    @DisplayName("모임 가입 동시성 이슈 테스트")
    @Test
    void concurrencyTest() throws InterruptedException {

        //given
        final int PARTICIPANT_PEOPLE = 5;
        final int MAXIMUM_PEOPLE = 2;

        Meeting meeting = meetingRepository.save(Meeting.builder()
                .ownerId(0L)
                .title("ConcurrencyTest")
                .content("ConcurrencyTest")
                .memberLimit(MAXIMUM_PEOPLE)
                .category(Category.HEALTH)
                .build());

        CountDownLatch countDownLatch = new CountDownLatch(PARTICIPANT_PEOPLE);

        for(int i = 1; i < 11; i++) {
            memberRepository.save(Member.builder()
                    .id((long) i)
                    .build()
            );
        }
        log.info("[모임 및 멤버 저장 완료]");
        AtomicInteger memberCount = new AtomicInteger(1);
        List<ParticipateWorker> workers = Stream
                .generate(() -> new ParticipateWorker(meeting.getId(), (long) memberCount.getAndIncrement(), countDownLatch))
                .limit(PARTICIPANT_PEOPLE)
                .collect(Collectors.toList());

        //when
        log.info("[스레드 시작]");
        workers.forEach(W -> new Thread(W).start());
        countDownLatch.await();

        //then
        List<MemberMeeting> memberMeetings = memberMeetingRepository.findALlByMeetingId(meeting.getId());
        long memberMeetingCount = memberMeetings.size();

        assertThat(memberMeetingCount).isEqualTo(MAXIMUM_PEOPLE);
    }

    private class ParticipateWorker implements Runnable {

        private final Long meetingId;
        private final Long memberId;
        private final CountDownLatch countDownLatch;

        public ParticipateWorker(Long meetingId, Long memberId, CountDownLatch countDownLatch) {
            this.meetingId = meetingId;
            this.memberId = memberId;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                meetingInquiryService.checkJoinRequestValid(meetingId, memberId);
            }
            catch (Exception e) {
                log.warn("*** [" + e.getClass().getSimpleName() + "]: " + e.getMessage());
            }
            finally {
                countDownLatch.countDown();
            }
        }
    }
}