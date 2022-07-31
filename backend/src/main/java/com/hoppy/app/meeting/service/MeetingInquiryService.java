package com.hoppy.app.meeting.service;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.MeetingDto;
import com.hoppy.app.meeting.dto.ParticipantDto;
import java.util.List;

/*
* 모임 조회 서비스
* 1. 카테고리에 속하는 모임 조회
* 2. 제목 검색 결과에 해당하는 모임 조회
* 3. ...
* */
public interface MeetingInquiryService {

    public List<Meeting> listMeetingByCategory(Category category, long lastId);

    public long getListsLastMeetingId(List<Meeting> meetingList);

    public String createNextPagingUrl(int categoryNumber, long lastId);

    public List<MeetingDto> meetingListToMeetingDtoList(List<Meeting> meetingList, Long memberId);

    public Meeting getMeetingById(Long id);

    public List<ParticipantDto> getParticipantDtoList(Meeting meeting);

    public Boolean checkLiked(Long meetingId, Long memberId);

    public void checkJoinRequestValid(Long meetingId, Long memberId);
}
