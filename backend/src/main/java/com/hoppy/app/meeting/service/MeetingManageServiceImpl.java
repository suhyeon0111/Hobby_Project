package com.hoppy.app.meeting.service;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.CreateMeetingDto;
import com.hoppy.app.meeting.dto.ParticipantDto;
import com.hoppy.app.meeting.repository.MeetingRepository;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.member.repository.MemberMeetingRepository;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingManageServiceImpl implements MeetingManageService {

    private final MeetingRepository meetingRepository;
    private final MemberMeetingRepository memberMeetingRepository;

    @Override
    @Transactional
    public void saveMeeting(Meeting meeting) {
        meetingRepository.save(meeting);
    }

    @Override
    @Transactional
    public void createAndSaveMemberMeetingData(Long meetingId, Long memberId) {
        memberMeetingRepository.save(MemberMeeting.builder()
                .meetingId(meetingId)
                .memberId(memberId)
                .build());
    }

    @Override
    public void checkJoinedMember(List<ParticipantDto> participantList, Long memberId) {
        boolean joined = participantList.stream().anyMatch(P -> Objects.equals(P.getId(), memberId));
        if(!joined) {
            throw new BusinessException(ErrorCode.NOT_JOINED);
        }
    }

    @Override
    public Meeting createMeeting(CreateMeetingDto dto, Long ownerId) throws BusinessException {
        if(checkTitleDuplicate(dto.getTitle())) {
            throw new BusinessException(ErrorCode.TITLE_DUPLICATE);
        }

        if(Category.intToCategory(dto.getCategory()) == Category.ERROR) {
            throw new BusinessException(ErrorCode.BAD_CATEGORY);
        }
        return Meeting.of(dto, ownerId);
    }

    @Override
    public boolean checkTitleDuplicate(String title) {
        return meetingRepository.findMeetingByTitle(title).isPresent();
    }

    @Override
    @Transactional
    public void withdrawMeeting(Long meetingId, Long memberId) {
        Optional<Meeting> optionalMeeting = meetingRepository.findMeetingByIdWithLock(meetingId);
        if(optionalMeeting.isEmpty()) {
            throw new BusinessException(ErrorCode.MEETING_NOT_FOUND);
        }

        Meeting meeting = optionalMeeting.get();
        Set<MemberMeeting> participants = meeting.getParticipants();

        boolean joined = participants.stream().anyMatch(M -> Objects.equals(M.getMemberId(), memberId));
        if(!joined) {
            throw new BusinessException(ErrorCode.NOT_JOINED);
        }

        if(meeting.isFull()) {
            meeting.setFullFlag(false);
        }
        memberMeetingRepository.deleteMemberMeetingByMeetingIdAndMemberId(meetingId, memberId);
    }
}
