package com.hoppy.app.meeting.service;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.MeetingDto;
import com.hoppy.app.meeting.dto.ParticipantDto;
import java.util.List;

public interface MeetingInquiryService {

    public List<Meeting> pagingMeetingList(Category category, long lastId);

    public void checkJoinedMember(List<ParticipantDto> participants, Long memberId);

    public long getLastId(List<Meeting> meetingList);

    public long validCheckLastId(long lastId);

    public String createNextPagingUrl(int categoryNumber, long lastId);

    public List<MeetingDto> listToDtoList(List<Meeting> meetingList, Long memberId);

    public Meeting getById(Long id);

    public List<ParticipantDto> getParticipants(Meeting meeting);

    public void checkJoinRequestValid(Long meetingId, Long memberId);
}
