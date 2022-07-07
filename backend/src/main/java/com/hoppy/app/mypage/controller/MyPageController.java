//package com.hoppy.app.mypage.controller;
//
//import com.hoppy.app.login.auth.provider.AuthTokenProvider;
//import com.hoppy.app.member.service.MemberDTOService;
//import com.hoppy.app.member.dto.MyPageMemberDto;
//import com.hoppy.app.member.service.UpdateMemberService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/mypage")
//public class MyPageController {
//    private final MemberDTOService memberDTOService;
//    private final UpdateMemberService updateMemberService;
//
//    private final AuthTokenProvider authTokenProvider;
//    /**
//     * /api/mypage?jwt={JSON_WEB_TOKEN} 요청이 들어오면 마이페이지에 노출되는 정보(이름, 프로필사진, 소개글)을 응답
//     * myMeetings, myMeetingLikes, 스토리 추가 필요
//     */
//    @GetMapping("/show")
//    public MyPageMemberDto getUserSocialId(@RequestParam("jwt") String code) {
//        MyPageMemberDto memberDto = memberDTOService.myPageResponse(code);
//        return memberDto;
//    }
//
//    @PutMapping("/update")
//    public MyPageMemberDto updateMember(@RequestParam("jwt") String code, @RequestParam("username") String username, @RequestParam("profileUrl") String profileUrl, @RequestParam("intro") String intro) {
//        updateMemberService.updateMember(code, username, profileUrl, intro);
//        MyPageMemberDto memberDto = memberDTOService.myPageResponse(code);
//        return memberDto;
//    }
//}
