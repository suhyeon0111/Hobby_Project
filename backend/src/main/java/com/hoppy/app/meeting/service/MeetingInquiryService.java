package com.hoppy.app.meeting.service;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.MeetingDto;
import com.hoppy.app.meeting.dto.ParticipantDto;
import com.hoppy.app.member.domain.Member;
import java.util.List;

public interface MeetingInquiryService {

    public List<Meeting> pagingMeetingList(Category category, long lastId);

    public void checkJoinedMemberV1(List<Member> participants, long memberId);

    public void checkJoinedMemberV2(List<ParticipantDto> participants, long memberId);

    public long getLastId(List<Meeting> meetingList);

    public long validCheckLastId(long lastId);

    public String createNextPagingUrl(int categoryNumber, long lastId);

    public List<MeetingDto> listToDtoList(List<Meeting> meetingList, long memberId);

    public Meeting getById(long id);

    public Meeting getByIdWithParticipants(long id);

    public List<Member> getParticipantList(Meeting meeting);

    public List<ParticipantDto> getParticipantDtoList(Meeting meeting);

    public void checkJoinRequestValid(long meetingId, long memberId);
}
