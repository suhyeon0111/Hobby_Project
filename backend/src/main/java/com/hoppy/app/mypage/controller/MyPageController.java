package com.hoppy.app.mypage.controller;

import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.login.auth.authentication.OAuth2UserDetails;
import com.hoppy.app.login.auth.provider.AuthTokenProvider;
import com.hoppy.app.login.auth.token.AuthToken;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.dto.RequestDto;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.member.service.MemberDTOService;
import com.hoppy.app.member.dto.MyPageMemberDto;
import com.hoppy.app.member.service.UpdateMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {
    private final MemberDTOService memberDTOService;
    private final UpdateMemberService updateMemberService;

<<<<<<< HEAD

    /**
     * /api/mypage?jwt={JSON_WEB_TOKEN} 요청이 들어오면 마이페이지에 노출되는 정보(이름, 프로필사진, 소개글)을 응답
     * myMeetings, myMeetingLikes, 스토리 추가 필요
     */
    @GetMapping("/show")
    public MyPageMemberDto getUserSocialId(@RequestParam("jwt") String code) {

        MyPageMemberDto memberDto = memberDTOService.myPageResponse(code);
        return memberDto;
    }

    @PutMapping("/update")
    public MyPageMemberDto updateMember(@RequestParam("jwt") String code, @RequestParam("username") String username, @RequestParam("profileUrl") String profileUrl, @RequestParam("intro") String intro) {
        updateMemberService.updateMember(code, username, profileUrl, intro);
        MyPageMemberDto memberDto = memberDTOService.myPageResponse(code);
        return memberDto;
=======
    private final MemberServiceImpl memberService;
    
    @GetMapping("/mypage")
    @ResponseBody
    public String getUserSocialId(@RequestParam("jwt") String code) {
        String socialPk = authTokenProvider.getSocialId(code);
        System.out.println("socialPk = " + socialPk);
        
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // Anonymous User

        Optional<Member> member = memberRepository.findBySocialTypeAndSocialId(SocialType.KAKAO, socialPk);
            /**
             * DB에서 회원 정보를 가져와 응답으로 넘겨줌.
             */
            if(member.isPresent()) {
                System.out.println("member.get().getUsername() = " + member.get().getUsername());
                System.out.println("member.get().getEmail() = " + member.get().getEmail());
                System.out.println("member.get().getProfileUrl() = " + member.get().getProfileUrl());
            } else {
                System.out.println("존재하지 않는 회원입니다.");
            }
        /**
         * 회원이 없으면, error 처리
         */
        return member.get().getProfileUrl();
<<<<<<< Updated upstream
=======
>>>>>>> 7e5aa59450141e616b8f68a5b10450a2152af840
>>>>>>> Stashed changes
    }

}
