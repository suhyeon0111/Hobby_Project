package com.hoppy.app.meeting.service;

import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.CreateMeetingDto;
import com.hoppy.app.meeting.dto.ParticipantDto;
import com.hoppy.app.member.domain.Member;
import java.util.List;

public interface MeetingManageService {

    public void saveMeeting(Meeting meeting);

    public void createAndSaveMemberMeetingData(Long meetingId, Long memberId);

    public Meeting createMeeting(CreateMeetingDto dto, Long ownerId);

    public boolean checkTitleDuplicate(String name);

    public void withdrawMeeting(Long meetingId, Long memberId);
}
