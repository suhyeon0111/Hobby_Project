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

        MemberMeeting memberMeeting = MemberMeeting.builder()
                .meeting(meeting)
                .member(member)
                .build();

        memberMeetingRepository.save(memberMeeting);
    }

    @Override
    public Meeting createMeeting(CreateMeetingDto dto) throws BusinessException {
        // 이름 중복 검사
        if(checkTitleDuplicate(dto.getTitle())) {
            throw new BusinessException(ErrorCode.TITLE_DUPLICATE);
        }

        // 카테고리 유효성 검사
        Category category = Category.intToEnum(dto.getCategory());
        if(category == Category.ERROR) {
            throw new BusinessException(ErrorCode.CATEGORY_ERROR);
        }

        return Meeting.builder()
                .category(category)
                .url("https://hoppyservice.s3.ap-northeast-2.amazonaws.com/" + dto.getFilename())
                .title(dto.getTitle())
                .content(dto.getContent())
                .memberLimit(dto.getMemberLimit())
                .build();
    }

    @Override
    public boolean checkTitleDuplicate(String title) {
        return 0 < meetingRepository.countMeetingByTitle(title);
    }
}
