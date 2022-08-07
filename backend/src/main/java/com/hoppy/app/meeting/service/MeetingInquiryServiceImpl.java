package com.hoppy.app.meeting.service;

import com.hoppy.app.like.service.MemberMeetingLikeService;
import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.MeetingDto;
import com.hoppy.app.meeting.repository.MeetingRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.like.domain.LikeManager;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.like.domain.MemberMeetingLike;
import com.hoppy.app.meeting.dto.ParticipantDto;
import com.hoppy.app.like.service.LikeManagerService;
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
    private final LikeManagerService likeManagerService;
    private final MemberMeetingLikeService memberMeetingLikeService;
    private final MeetingRepository meetingRepository;
    private final MemberMeetingRepository memberMeetingRepository;

    @Override
    public List<Meeting> getMeetingByCategory(Category category, long lastId) {
        return meetingRepository.infiniteScrollPagingMeeting(category, lastId, PageRequest.of(0, 14));
    }

    @Override
    public long getLastId(List<Meeting> meetingList) {
        return meetingList.get(meetingList.size() - 1).getId() - 1;
    }

    @Override
    public String createNextPagingUrl(int categoryNumber, long lastId) {
        if(lastId >= 0)
            return "https://hoppy.kro.kr/api/meeting?categoryNumber=" + categoryNumber + "&lastId=" + lastId;
        else
            return "end";
    }

    @Override
    public List<MeetingDto> listToDtoList(List<Meeting> meetingList, Long memberId) {
        Member member = memberService.findMemberById(memberId);
        LikeManager likeManager = likeManagerService.getMemberMeetingLikes(member);

        Map<Long, Boolean> likedMap = likeManager.getMeetingLikes()
                .stream()
                .map(MemberMeetingLike::getMeetingId)
                .collect(Collectors.toMap(L -> L, L -> Boolean.TRUE));

        return meetingList.stream()
                .map(M -> MeetingDto.meetingToMeetingDto(M, likedMap.containsKey(M.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public Meeting getMeetingById(Long id) {
        Optional<Meeting> meeting = meetingRepository.findById(id);
        if(meeting.isEmpty()) {
            throw new BusinessException(ErrorCode.MEETING_NOT_FOUND);
        }
        return meeting.get();
    }

    @Override
    public List<ParticipantDto> getParticipantDtoList(Meeting meeting) {
        List<Long> memberIdList = meeting.getParticipants()
                .stream()
                .map(MemberMeeting::getMemberId)
                .sorted()
                .collect(Collectors.toList());

        List<Member> memberList = memberService.infiniteScrollPagingMember(memberIdList, 0L, PageRequest.of(0, memberIdList.size()));

        return memberList
                .stream()
                .map(M -> ParticipantDto.memberToParticipantDto(M, meeting.getOwnerId()))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean checkLiked(Long meetingId, Long memberId) {
        Member member = memberService.findMemberById(memberId);
        return memberMeetingLikeService.findMemberMeetingLikeByLikeManagerAndMeetingId(member.getLikeManager(), meetingId).isPresent();
    }

    @Override
    @Transactional
    public void checkJoinRequestValid(Long meetingId, Long memberId) {
        Optional<Meeting> optionalMeeting = meetingRepository.findMeetingByIdWithLock(meetingId);
        if(optionalMeeting.isEmpty()) {
            throw new BusinessException(ErrorCode.MEETING_NOT_FOUND);
        }

        Meeting meeting = optionalMeeting.get();
        if(meeting.isFull()) {
            throw new BusinessException(ErrorCode.MAX_PARTICIPANTS);
        }

        Set<MemberMeeting> participants = meeting.getParticipants();

        boolean alreadyJoin = participants.stream().anyMatch(M -> Objects.equals(M.getMemberId(), memberId));
        if(alreadyJoin) {
            throw new BusinessException(ErrorCode.ALREADY_JOIN);
        }

        memberMeetingRepository.save(MemberMeeting.of(memberId, meetingId));
        if(participants.size() + 1 == meeting.getMemberLimit()) {
            meeting.setFullFlag(true);
        }
    }
}
