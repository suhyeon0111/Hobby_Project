package com.hoppy.app.meeting.controller;

import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.CreateMeetingDto;
import com.hoppy.app.meeting.service.MeetingInquiryService;
import com.hoppy.app.meeting.service.MeetingManageService;
import com.hoppy.app.response.dto.ResponseDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingInquiryService meetingInquiryService;
    private final MeetingManageService meetingManageService;

    @PostMapping
    public ResponseEntity<ResponseDto> createMeeting(
            @Valid CreateMeetingDto dto
//            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        Meeting meeting = meetingManageService.createMeeting(dto);
//        meetingManageService.createAndSaveMemberMeetingData(meeting, member);
        meetingManageService.saveMeeting(meeting);
        return null;
    }
}
