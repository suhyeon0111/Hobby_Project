package com.hoppy.app.meeting.controller;

import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.CreateMeetingDto;
import com.hoppy.app.meeting.service.MeetingInquiryService;
import com.hoppy.app.meeting.service.MeetingManageService;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.service.ResponseService;
import com.hoppy.app.response.service.SuccessCode;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingInquiryService meetingInquiryService;
    private final MeetingManageService meetingManageService;
    private final MemberService memberService;
    private final ResponseService responseService;

    @PostMapping
    public ResponseEntity<ResponseDto> createMeeting(@RequestBody @Valid CreateMeetingDto dto) {
        Meeting meeting = meetingManageService.createMeeting(dto);
        Member member = memberService.findMemberById(dto.getMemberId());

        /*
        * 아래에 있는 두개의 save 메서드 호출 순서를 서로 바꾸면
        * TransientPropertyValueException 이 발생함
        * 명확한 이유를 알아야할 필요가 있다.
        * */
        meetingManageService.saveMeeting(meeting);
        meetingManageService.createAndSaveMemberMeetingData(meeting, member);

        return responseService.successResult(SuccessCode.CREATE_MEETING_SUCCESS);
    }
}
