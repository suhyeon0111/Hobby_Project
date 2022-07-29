package com.hoppy.app.meeting.controller;

import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.CreateMeetingDto;
import com.hoppy.app.meeting.dto.MeetingDetailDto;
import com.hoppy.app.meeting.dto.MeetingDto;
import com.hoppy.app.meeting.dto.MeetingJoinDto;
import com.hoppy.app.meeting.dto.MeetingWithdrawalDto;
import com.hoppy.app.meeting.dto.PagingMeetingDto;
import com.hoppy.app.meeting.service.MeetingInquiryService;
import com.hoppy.app.meeting.service.MeetingManageService;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.meeting.dto.ParticipantDto;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import com.hoppy.app.response.service.ResponseService;
import com.hoppy.app.response.service.SuccessCode;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<ResponseDto> createMeeting(
            @RequestBody @Valid CreateMeetingDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Member member = memberService.findMemberById(userDetails.getId());
        Meeting meeting = meetingManageService.createMeeting(dto, member.getId());

        meetingManageService.saveMeeting(meeting);
        meetingManageService.createAndSaveMemberMeetingData(meeting.getId(), member.getId());

        return responseService.successResult(SuccessCode.CREATE_MEETING_SUCCESS);
    }

    @PostMapping("/entry")
    public ResponseEntity<ResponseDto> joinMeeting(
            @RequestBody @Valid MeetingJoinDto meetingJoinDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long meetingId = meetingJoinDto.getMeetingId();
        Meeting meeting = meetingInquiryService.getMeetingById(meetingId);
        meetingInquiryService.checkJoinRequestValid(meeting, userDetails.getId());
        meetingManageService.createAndSaveMemberMeetingData(meeting.getId(), userDetails.getId());

        return responseService.successResult(SuccessCode.JOIN_MEETING_SUCCESS);
    }

    @DeleteMapping("/withdrawal")
    public ResponseEntity<ResponseDto> withdrawalMeeting(
            @RequestBody @Valid MeetingWithdrawalDto meetingWithdrawalDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        meetingManageService.withdrawMeeting(meetingWithdrawalDto.getMeetingId(), userDetails.getId());

        return responseService.successResult(SuccessCode.WITHDRAW_MEETING_SUCCESS);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getMeetingListByCategoryWithPaging(
            @RequestParam int categoryNumber,
            @RequestParam(value = "lastId", defaultValue = "0") long lastId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Category category = Category.intToCategory(categoryNumber);
        if(category == Category.ERROR) {
            throw new BusinessException(ErrorCode.CATEGORY_ERROR);
        }

        List<Meeting> meetingList = meetingInquiryService.listMeetingByCategory(category, lastId);
        lastId = meetingInquiryService.getListsLastMeetingId(meetingList);
        String nextPagingUrl = meetingInquiryService.createNextPagingUrl(categoryNumber, lastId);
        List<MeetingDto> meetingDtoList = meetingInquiryService.meetingListToMeetingDtoList(meetingList, userDetails.getId());
        PagingMeetingDto pagingMeetingDto = PagingMeetingDto.builder()
                .meetingList(meetingDtoList)
                .nextPagingUrl(nextPagingUrl)
                .build();

        return responseService.successResult(SuccessCode.INQUIRY_MEETING_SUCCESS, pagingMeetingDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getMeetingDetail(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Meeting meeting = meetingInquiryService.getMeetingById(id);
        List<ParticipantDto> participantList = meetingInquiryService.getParticipantDtoList(meeting);
        Boolean liked = meetingInquiryService.checkLiked(meeting.getId(), userDetails.getId());
        MeetingDetailDto meetingDetailDto = MeetingDetailDto.of(meeting, participantList, liked);

        return responseService.successResult(SuccessCode.INQUIRE_MEETING_DETAIL_SUCCESS, meetingDetailDto);
    }
}
