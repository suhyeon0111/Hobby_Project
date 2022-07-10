package com.hoppy.app.meeting.controller;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.CreateMeetingDto;
import com.hoppy.app.meeting.dto.MeetingDto;
import com.hoppy.app.meeting.dto.PagingMeetingDto;
import com.hoppy.app.meeting.service.MeetingInquiryService;
import com.hoppy.app.meeting.service.MeetingManageService;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import com.hoppy.app.response.service.ResponseService;
import com.hoppy.app.response.service.SuccessCode;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        Member member = memberService.findMemberById(dto.getMemberId());
        Meeting meeting = meetingManageService.createMeeting(dto);

        /*
        * 아래에 있는 두개의 save 메서드 호출 순서를 서로 바꾸면
        * TransientPropertyValueException 이 발생함
        * 명확한 이유를 알아야할 필요가 있다.
        * */
        meetingManageService.saveMeeting(meeting);
        meetingManageService.createAndSaveMemberMeetingData(meeting, member);

        return responseService.successResult(SuccessCode.CREATE_MEETING_SUCCESS);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getMeetingListByCategoryWithPaging(
            @RequestParam int categoryNumber,
            @RequestParam long lastId
    ) {
        Category category = Category.intToCategory(categoryNumber);
        if(category == Category.ERROR) {
            throw new BusinessException(ErrorCode.CATEGORY_ERROR);
        }

        Long memberId = 1L; // 유저 아이디

        List<Meeting> meetingList = meetingInquiryService.listMeetingByCategory(category, lastId);
        lastId = meetingInquiryService.getListsLastMeetingId(meetingList);
        String nextPagingUrl = meetingInquiryService.createNextPagingUrl(categoryNumber, lastId);
        List<MeetingDto> meetingDtoList = meetingInquiryService.meetingListToMeetingDtoList(meetingList, memberId);
        PagingMeetingDto pagingMeetingDto = PagingMeetingDto.builder()
                .meetingDtoList(meetingDtoList)
                .nextPagingUrl(nextPagingUrl)
                .build();

        return responseService.successResult(SuccessCode.INQUIRY_MEETING_SUCCESS, pagingMeetingDto);
    }
}
