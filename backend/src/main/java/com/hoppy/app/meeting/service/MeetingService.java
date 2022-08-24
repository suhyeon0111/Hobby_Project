package com.hoppy.app.meeting.service;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.CreateMeetingDto;
import com.hoppy.app.meeting.dto.MeetingDto;
import com.hoppy.app.meeting.dto.PagingMeetingDto;
import com.hoppy.app.meeting.dto.ParticipantDto;
import com.hoppy.app.member.domain.Member;
import java.util.List;

public interface MeetingService {

    public void createAndSaveMemberMeetingData(Meeting meeting, Member member);
    public boolean checkTitleDuplicate(String name);
    public Meeting createMeeting(CreateMeetingDto dto, Long ownerId);
    public Meeting findById(long id);
    public Meeting findByIdWithParticipants(long id);

    public void withdrawMeeting(Long meetingId, Long memberId);
    public void checkJoinRequestValid(long meetingId, long memberId);
    public void checkJoinedMemberV1(List<Member> participants, long memberId);
    public void checkJoinedMemberV2(List<ParticipantDto> participants, long memberId);
    public long validCheckLastId(long lastId);
    public long getLastId(List<Meeting> meetingList);
    public String createNextPagingUrl(int categoryNumber, long lastId);
    public List<Meeting> pagingMeetingList(Category category, long lastId);
    public List<MeetingDto> listToDtoList(List<Meeting> meetingList, long memberId);
    public PagingMeetingDto pagingMeeting(int categoryNumber, long lastId, long memberId);

    public List<Member> getParticipantList(Meeting meeting);
    public List<ParticipantDto> getParticipantDtoList(Meeting meeting);

    public void likeMeeting(long memberId, long meetingId);
}
