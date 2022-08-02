package com.hoppy.app.meeting.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static reactor.core.publisher.Mono.when;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.ParticipantDto;
import com.hoppy.app.meeting.repository.MeetingRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.like.repository.MemberMeetingLikeRepository;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    void getParticipantDtoList() {
        // given
        Long ownerId = 5L;
        Meeting meeting;
        Set<MemberMeeting> participants = new HashSet<>();
        List<Long> memberIdList = new ArrayList<>();
        List<Member> memberList = new ArrayList<>();

        for(int i = 1; i <= 10; i++) {
            participants.add(MemberMeeting.builder()
                    .memberId((long) i)
                    .meetingId(1L)
                    .build());

            memberIdList.add((long) i);

            memberList.add(Member.builder()
                    .id((long) i)
                    .build());
        }
        meeting = Meeting.builder()
                .ownerId(ownerId)
                .title("test")
                .content("test")
                .memberLimit(15)
                .category(Category.HEALTH)
                .participants(participants)
                .build();

        Mockito.when(memberService.infiniteScrollPagingMember(memberIdList, 0L, PageRequest.of(0, memberIdList.size())))
                .thenReturn(memberList);

        // when
        List<ParticipantDto> participantList = meetingInquiryService.getParticipantDtoList(meeting);

        // then
        Assertions.assertThat(participantList.size()).isEqualTo(memberList.size());

        participantList = participantList.stream().filter(ParticipantDto::getOwner).collect(Collectors.toList());
        Assertions.assertThat(participantList.size()).isEqualTo(1);
        Assertions.assertThat(participantList.get(0).getId()).isEqualTo(ownerId);
    }

    @DisplayName("모임 가입 요청 MAX_PARTICIPANTS 예외 발생 테스트")
    @Test
    void checkJoinRequestValidFailTest1() {

        //given
        Meeting meeting = Meeting.builder()
                .ownerId(1L)
                .title("test")
                .content("test")
                .memberLimit(5)
                .category(Category.HEALTH)
                .fullFlag(true)
                .build();

        long requestMemberId = 0L;

        //when
        Mockito.when(meetingRepository.findMeetingByIdWithLock(meeting.getId())).thenReturn(Optional.of(meeting));
        BusinessException exception = assertThrows(BusinessException.class, () -> meetingInquiryService.checkJoinRequestValid(meeting.getId(), requestMemberId));

        //then
        assertEquals(ErrorCode.MAX_PARTICIPANTS.getMessage(), exception.getMessage());
    }

    @DisplayName("모임 가입 요청 ALREADY_JOIN 예외 발생 테스트")
    @Test
    void checkJoinRequestValidFailTest2() {

        //given
        Set<MemberMeeting> participants = new HashSet<>();
        long requestMemberId = 0L;
        participants.add(MemberMeeting.builder()
                .memberId(requestMemberId)
                .meetingId(0L)
                .build()
        );
        Meeting meeting = Meeting.builder()
                .ownerId(1L)
                .title("test")
                .content("test")
                .memberLimit(5)
                .category(Category.HEALTH)
                .participants(participants)
                .build();

        //when
        Mockito.when(meetingRepository.findMeetingByIdWithLock(meeting.getId())).thenReturn(Optional.of(meeting));
        BusinessException exception = assertThrows(BusinessException.class, () -> meetingInquiryService.checkJoinRequestValid(meeting.getId(), requestMemberId));

        //then
        assertEquals(ErrorCode.ALREADY_JOIN.getMessage(), exception.getMessage());
    }
}