package com.hoppy.app.member.controller;

import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.member.repository.MemberMeetingRepoTest;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.member.service.MemberMeetingServiceImpl;
import com.hoppy.app.member.service.MemberServiceImpl;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.service.ResponseService;
import com.hoppy.app.response.service.SuccessCode;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting")

public class MemberMeetingController {

    private final MemberRepository memberRepository;
    private final MemberServiceImpl memberService;
    private final MemberMeetingServiceImpl meetingService;
    private final ResponseService responseService;

    private final MemberMeetingRepoTest memberMeetingRepoTest;
    /**
     * 이미 가입한 모임일 경우, 예외 처리를 하려 했으나, 단순히 프론트 측에서 '가입하기' 버튼만 숨기면 될 듯 싶어서 보류
     */

    @GetMapping("/join")
    public ResponseEntity<ResponseDto> joinMeeting (
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam("meetingId") String meetingId
    ) {
        Long id = customUserDetails.getId();
        meetingService.joinToMeetingById(id, Long.parseLong(meetingId));
        /*Set<MemberMeeting> set = new HashSet<>();
        Iterator<MemberMeeting> iter = set.iterator();
        while (iter.hasNext()) {
            System.out.println("iter.next() = " + iter.next().getMeetingId());
        }*/
        return responseService.successResult(SuccessCode.SHOW_PROFILE_SUCCESS);
    }

    @GetMapping("/test")
    public void testMeeting(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long id = userDetails.getId();
        System.out.println("id = " + id);
        memberMeetingRepoTest.meetingRepoTest(id);
        System.out.println("MemberMeetingController.testMeeting");
    }
}
