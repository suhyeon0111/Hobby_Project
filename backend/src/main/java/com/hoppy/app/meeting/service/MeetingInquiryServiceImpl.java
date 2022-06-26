package com.hoppy.app.meeting.service;

import com.hoppy.app.meeting.common.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.repository.MeetingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingInquiryServiceImpl implements MeetingInquiryService {

    private final MeetingRepository meetingRepository;

    @Override
    public void listMeetingByCategory(Category category) {

        List<Meeting> meetingList = meetingRepository.findAllMeetingByCategoryUsingFetch(category);


    }
}
