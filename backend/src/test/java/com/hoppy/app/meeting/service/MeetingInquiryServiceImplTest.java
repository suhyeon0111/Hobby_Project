package com.hoppy.app.meeting.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.ParticipantDto;
import com.hoppy.app.meeting.repository.MeetingRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

/**
 * @author 태경 2022-07-18
 */
@ExtendWith(MockitoExtension.class)
//@TestInstance(Lifecycle.PER_CLASS)
@TestInstance(Lifecycle.PER_METHOD) // default
class MeetingInquiryServiceImplTest {

    @InjectMocks
    MeetingInquiryServiceImpl meetingInquiryService;

    @Mock
    MemberService memberService;

    @Mock
    MeetingRepository meetingRepository;

    @DisplayName("ParticipantDtoList 반환 검증 테스트")
    @Test
    void getParticipantDtoListTest() {
        // given
        final long OWNER_ID = 5L;
        final int PARTICIPANT_COUNT = 10;

        List<Long> memberIdList = new ArrayList<>();
        List<Member> memberList = new ArrayList<>();

        Meeting meeting = Meeting.builder()
                .ownerId(OWNER_ID)
                .title("test")
                .content("test")
                .memberLimit(15)
                .category(Category.HEALTH)
                .build();

        for (int i = 1; i <= PARTICIPANT_COUNT; i++) {
            Member member = Member.builder()
                    .id((long) i)
                    .build();
            meeting.addParticipant(MemberMeeting.builder()
                    .member(member)
                    .meeting(meeting)
                    .build());
            memberList.add(member);
            memberIdList.add(member.getId());
        }
        given(memberService.infiniteScrollPagingMember(memberIdList, 0L,
                PageRequest.of(0, memberIdList.size())))
                .willReturn(memberList);

        // when
        List<ParticipantDto> participantList = meetingInquiryService.getParticipantDtoList(meeting);

        // then
        assertThat(participantList.size()).isEqualTo(memberList.size());
        assertThat(participantList.stream().filter(ParticipantDto::getOwner).count()).isEqualTo(1);
    }

    @DisplayName("모임 가입 요청 MAX_PARTICIPANTS 예외 발생 테스트")
    @Test
    void checkJoinRequestValidFailTest1() {
        //given
        Meeting meeting = Meeting.builder()
                .ownerId(1L)
                .title("test")
                .content("test")
                .memberLimit(0)
                .category(Category.HEALTH)
                .fullFlag(true)
                .build();

        final var REQUEST_MEMBER_ID = 1111L;
        final var REQUEST_MEETING_ID = 2222L;

        given(meetingRepository.findWithParticipantsByIdUsingLock(REQUEST_MEETING_ID)).willReturn(
                Optional.of(meeting));

        //when
        BusinessException exception = assertThrows(BusinessException.class,
                () -> meetingInquiryService.checkJoinRequestValid(REQUEST_MEETING_ID,
                        REQUEST_MEMBER_ID));

        //then
        assertEquals(ErrorCode.MAX_PARTICIPANTS.getMessage(), exception.getMessage());
    }

    @DisplayName("모임 가입 요청 ALREADY_JOIN 예외 발생 테스트")
    @Test
    void checkJoinRequestValidFailTest2() {
        //given
        Set<MemberMeeting> participants = new HashSet<>();
        final var REQUEST_MEMBER_ID = 1111L;
        final var REQUEST_MEETING_ID = 2222L;
        Member member = Member.builder()
                .id(REQUEST_MEMBER_ID)
                .build();
        Meeting meeting = Meeting.builder()
                .ownerId(1L)
                .title("test")
                .content("test")
                .memberLimit(5)
                .category(Category.HEALTH)
                .build();
        meeting.addParticipant(
                MemberMeeting.builder()
                        .member(member)
                        .meeting(meeting)
                        .build()
        );

        given(meetingRepository.findWithParticipantsByIdUsingLock(REQUEST_MEETING_ID)).willReturn(
                Optional.of(meeting));

        //when
        BusinessException exception = assertThrows(BusinessException.class,
                () -> meetingInquiryService.checkJoinRequestValid(REQUEST_MEETING_ID, REQUEST_MEMBER_ID));

        //then
        assertEquals(ErrorCode.ALREADY_JOIN.getMessage(), exception.getMessage());
    }

    @DisplayName("모임 가입 요청 MEETING_NOT_FOUND 예외 발생 테스트")
    @Test
    void checkJoinRequestValidFailTest3() {
        //given
        final var REQUEST_MEMBER_ID = 1111L;
        final var REQUEST_MEETING_ID = 2222L;
        given(meetingRepository.findWithParticipantsByIdUsingLock(REQUEST_MEETING_ID)).willReturn(
                Optional.empty());

        //when
        BusinessException exception = assertThrows(BusinessException.class,
                () -> meetingInquiryService.checkJoinRequestValid(REQUEST_MEETING_ID, REQUEST_MEMBER_ID));

        //then
        assertEquals(ErrorCode.MEETING_NOT_FOUND.getMessage(), exception.getMessage());
    }
}