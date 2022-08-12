package com.hoppy.app.meeting.service;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.MeetingDto;
import com.hoppy.app.meeting.repository.MeetingRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.meeting.dto.ParticipantDto;
import com.hoppy.app.member.repository.MemberMeetingRepository;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MeetingInquiryServiceImpl implements MeetingInquiryService {

    private final MemberService memberService;
    private final MeetingRepository meetingRepository;
    private final MemberMeetingRepository memberMeetingRepository;

    @Override
    public List<Meeting> pagingMeetingList(Category category, long lastId) {
        return meetingRepository.infiniteScrollPaging(category, lastId, PageRequest.of(0, 14));
    }

    @Override
    public void checkJoinedMemberV1(List<Member> participants, long memberId) {
        boolean joined = participants.stream().anyMatch(P -> Objects.equals(P.getId(), memberId));
        if(!joined) {
            throw new BusinessException(ErrorCode.NOT_JOINED);
        }
    }

    @Override
    public void checkJoinedMemberV2(List<ParticipantDto> participants, long memberId) {
        boolean joined = participants.stream().anyMatch(P -> Objects.equals(P.getId(), memberId));
        if(!joined) {
            throw new BusinessException(ErrorCode.NOT_JOINED);
        }
    }

    @Override
    public long getLastId(List<Meeting> meetingList) {
        return meetingList.get(meetingList.size() - 1).getId() - 1;
    }

    @Override
    public long validCheckLastId(long lastId) {
        if(lastId == 0) {
            return Long.MAX_VALUE;
        }
        else if(lastId < 0) {
            throw new BusinessException(ErrorCode.NO_MORE_POST);
        }
        return lastId;
    }

    @Override
    public String createNextPagingUrl(int categoryNumber, long lastId) {
        if(lastId >= 0)
            return "https://hoppy.kro.kr/api/meeting?categoryNumber=" + categoryNumber + "&lastId=" + lastId;
        else
            return "end";
    }

    @Override
    public List<MeetingDto> listToDtoList(List<Meeting> meetingList, long memberId) {
        List<Long> meetingLikes = memberService.getMeetingLikes(memberId);

        Map<Long, Boolean> likedMap = meetingLikes.stream()
                .collect(Collectors.toMap(L -> L, L -> Boolean.TRUE));

        return meetingList.stream()
                .map(M -> MeetingDto.meetingToMeetingDto(M, likedMap.containsKey(M.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public Meeting getById(long id) {
        Optional<Meeting> meeting = meetingRepository.findById(id);
        if(meeting.isEmpty()) {
            throw new BusinessException(ErrorCode.MEETING_NOT_FOUND);
        }
        return meeting.get();
    }

    @Override
    public Meeting getByIdWithParticipants(long id) {
        Optional<Meeting> meeting = meetingRepository.findWithParticipantsById(id);
        if(meeting.isEmpty()) {
            throw new BusinessException(ErrorCode.MEETING_NOT_FOUND);
        }
        return meeting.get();
    }

    @Override
    public List<Member> getParticipantList(Meeting meeting) {
        List<Long> memberIdList = meeting.getParticipants()
                .stream()
                .map(M -> M.getMember().getId())
                .sorted()
                .collect(Collectors.toList());

        return memberService.infiniteScrollPagingMember(memberIdList, 0L, PageRequest.of(0, memberIdList.size()));
    }

    @Override
    public List<ParticipantDto> getParticipantDtoList(Meeting meeting) {
        return getParticipantList(meeting)
                .stream()
                .map(M -> ParticipantDto.memberToParticipantDto(M, meeting.getOwnerId()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void checkJoinRequestValid(long meetingId, long memberId) {
        Optional<Meeting> optionalMeeting = meetingRepository.findWithParticipantsByIdUsingLock(meetingId);
        if(optionalMeeting.isEmpty()) {
            throw new BusinessException(ErrorCode.MEETING_NOT_FOUND);
        }
        Meeting meeting = optionalMeeting.get();
        if(meeting.isFull()) {
            throw new BusinessException(ErrorCode.MAX_PARTICIPANTS);
        }
        Member member = memberService.findById(memberId);

        Set<MemberMeeting> participants = meeting.getParticipants();
        boolean alreadyJoin = participants.stream().anyMatch(M -> Objects.equals(M.getMemberId(), memberId));
        if(alreadyJoin) {
            throw new BusinessException(ErrorCode.ALREADY_JOIN);
        }

        memberMeetingRepository.save(MemberMeeting.of(member, meeting));
        if(participants.size() + 1 == meeting.getMemberLimit()) {
            meeting.setFullFlag(true);
        }
    }
}
