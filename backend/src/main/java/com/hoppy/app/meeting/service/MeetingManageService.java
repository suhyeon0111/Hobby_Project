package com.hoppy.app.meeting.service;

import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.CreateMeetingDto;
import com.hoppy.app.member.domain.Member;

public interface MeetingManageService {

    public void saveMeeting(Meeting meeting);

    public void createAndSaveMemberMeetingData(Meeting meeting, Member member);

    public Meeting createMeeting(CreateMeetingDto dto);

    public boolean checkTitleDuplicate(String name);
}
