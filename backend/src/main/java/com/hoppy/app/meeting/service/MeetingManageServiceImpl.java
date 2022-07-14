package com.hoppy.app.meeting.service;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.CreateMeetingDto;
import com.hoppy.app.meeting.repository.MeetingRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.member.repository.MemberMeetingRepository;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingManageServiceImpl implements MeetingManageService {

    private final MeetingRepository meetingRepository;
    private final MemberMeetingRepository memberMeetingRepository;

    @Override
    public void saveMeeting(Meeting meeting) {
        meetingRepository.save(meeting);
    }

    @Override
    public void createAndSaveMemberMeetingData(Meeting meeting, Member member) {
        memberMeetingRepository.save(MemberMeeting.builder()
                .meetingId(meeting.getId())
                .memberId(member.getId())
                .build());
    }

    @Override
    public Meeting createMeeting(CreateMeetingDto dto, Long ownerId) throws BusinessException {
        if(checkTitleDuplicate(dto.getTitle())) {
            throw new BusinessException(ErrorCode.TITLE_DUPLICATE);
        }

        if(Category.intToCategory(dto.getCategory()) == Category.ERROR) {
            throw new BusinessException(ErrorCode.CATEGORY_ERROR);
        }

        return Meeting.of(dto, ownerId);
    }

    @Override
    public boolean checkTitleDuplicate(String title) {
        return meetingRepository.findMeetingByTitle(title).isPresent();
    }
}
