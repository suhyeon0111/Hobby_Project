package com.hoppy.app.meeting.service;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.MeetingDto;
import com.hoppy.app.meeting.repository.MeetingRepository;
import com.hoppy.app.member.domain.MemberMeetingLike;
import com.hoppy.app.member.repository.MemberMeetingLikeRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingInquiryServiceImpl implements MeetingInquiryService {

    private final MeetingRepository meetingRepository;
    private final MemberMeetingLikeRepository memberMeetingLikeRepository;

    @Override
    public List<Meeting> listMeetingByCategory(Category category, long lastId) {
        return meetingRepository.infiniteScrollPagingMeeting(category, lastId, PageRequest.of(0, 14));
    }

    @Override
    public long getListsLastMeetingId(List<Meeting> meetingList) {
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
    public List<MeetingDto> meetingListToMeetingDtoList(List<Meeting> meetingList, Long memberId) {
        List<Long> likeIdList = memberMeetingLikeRepository
                .findAllByMemberId(memberId)
                .stream()
                .map(MemberMeetingLike::getMeetingId)
                .collect(Collectors.toList());

        return meetingList.stream()
                .map(M -> MeetingDto.meetingToMeetingDto(M, likeIdList.contains(M.getId())))
                .collect(Collectors.toList());
    }
}
