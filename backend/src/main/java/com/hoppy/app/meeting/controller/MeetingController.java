package com.hoppy.app.meeting.controller;

import com.hoppy.app.community.dto.PagingPostDto;
import com.hoppy.app.community.dto.PostDto;
import com.hoppy.app.community.service.PostService;
import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.CreateMeetingDto;
import com.hoppy.app.meeting.dto.MeetingDetailDto;
import com.hoppy.app.meeting.dto.MeetingDto;
import com.hoppy.app.meeting.dto.MeetingJoinDto;
import com.hoppy.app.meeting.dto.MeetingWithdrawalDto;
import com.hoppy.app.meeting.dto.PagingMeetingDto;
import com.hoppy.app.meeting.service.MeetingService;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.meeting.dto.ParticipantDto;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.service.ResponseService;
import com.hoppy.app.response.service.SuccessCode;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingService meetingService;
    private final MemberService memberService;
    private final PostService postService;
    private final ResponseService responseService;

    @PostMapping
    public ResponseEntity<ResponseDto> createMeeting(
            @RequestBody @Valid CreateMeetingDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Member member = memberService.findById(userDetails.getId());
        Meeting meeting = meetingService.createMeeting(dto, member.getId());
        meetingService.createAndSaveMemberMeetingData(meeting, member);

        return responseService.successResult(SuccessCode.CREATE_MEETING_SUCCESS);
    }

    @PostMapping("/entry")
    public ResponseEntity<ResponseDto> joinMeeting(
            @RequestBody @Valid MeetingJoinDto meetingJoinDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long meetingId = meetingJoinDto.getMeetingId();
        meetingService.checkJoinRequestValid(meetingId, userDetails.getId());

        return responseService.successResult(SuccessCode.JOIN_MEETING_SUCCESS);
    }

    @DeleteMapping("/withdrawal")
    public ResponseEntity<ResponseDto> withdrawalMeeting(
            @RequestBody @Valid MeetingWithdrawalDto meetingWithdrawalDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        meetingService.withdrawMeeting(meetingWithdrawalDto.getMeetingId(), userDetails.getId());

        return responseService.successResult(SuccessCode.WITHDRAW_MEETING_SUCCESS);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getMeetingListByCategoryWithPaging(
            @RequestParam int categoryNumber,
            @RequestParam(value = "lastId", defaultValue = "0") long lastId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        lastId = meetingService.checkLastIdValid(lastId);
        Category category = Category.intToCategory(categoryNumber);

        List<Meeting> meetingList = meetingService.pagingMeetingList(category, lastId);
        lastId = meetingService.getLastId(meetingList);
        String nextPagingUrl = meetingService.createNextPagingUrl(categoryNumber, lastId);
        List<MeetingDto> meetingDtoList = meetingService.listToDtoList(meetingList, userDetails.getId());
        PagingMeetingDto pagingMeetingDto = PagingMeetingDto.of(meetingDtoList, nextPagingUrl);

        return responseService.successResult(SuccessCode.INQUIRY_MEETING_SUCCESS, pagingMeetingDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getMeetingDetail(
            @PathVariable("id") long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Meeting meeting = meetingService.getById(id);
        List<ParticipantDto> participantList = meetingService.getParticipantDtoList(meeting);
        meetingService.checkJoinedMemberV2(participantList, userDetails.getId());

        boolean liked = memberService.checkMeetingLiked(userDetails.getId(), meeting.getId());
        MeetingDetailDto meetingDetailDto = MeetingDetailDto.of(meeting, participantList, liked);

        return responseService.successResult(SuccessCode.INQUIRE_MEETING_DETAIL_SUCCESS, meetingDetailDto);
    }

    @GetMapping("/posts")
    public ResponseEntity<ResponseDto> getPostsWithPaging(
            @RequestParam(value = "meetingId", defaultValue = "0") long meetingId,
            @RequestParam(value = "lastId", defaultValue = "0") long lastId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        lastId = postService.validCheckLastId(lastId);
        Meeting meeting = meetingService.getById(meetingId);
        List<Member> participantList = meetingService.getParticipantList(meeting);
        meetingService.checkJoinedMemberV1(participantList, userDetails.getId());

        List<PostDto> postDtoList = postService.pagingPostListV2(meeting, lastId, userDetails.getId());
        long lastPostId = postService.getLastId(postDtoList);
        String nextPagingUrl = postService.createNextPagingUrl(meetingId, lastPostId);
        PagingPostDto pagingPostDto = PagingPostDto.of(postDtoList, nextPagingUrl);

        return responseService.successResult(SuccessCode.INQUIRY_COMMUNITY_POSTS_SUCCESS, pagingPostDto);
    }
}
