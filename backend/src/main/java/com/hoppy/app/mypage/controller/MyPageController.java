package com.hoppy.app.mypage.controller;

import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.login.auth.provider.AuthTokenProvider;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.member.service.MemberServiceImpl;
import java.util.HashMap;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MyPageController {
    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberRepository;

    private final MemberServiceImpl memberService;
    
    @GetMapping("/mypage")
    public void getUserSocialId(@RequestParam("jwt") String code) {
        String socialPk = authTokenProvider.getSocialId(code);
        Member member = memberService.findMemberById((1L));
        if(member != null) {
            System.out.println("member.getUsername() = " + member.getUsername());
            System.out.println("member.getProfileUrl() = " + member.getProfileUrl());
//            System.out.println("member.getSelfInfo() = " + member.getSelfInfo());
//        Optional<Member> member = memberRepository.findBySocialTypeAndSocialId(SocialType.KAKAO, socialPk);
            /**
             * DB에서 회원 정보를 가져와 응답으로 넘겨줌.
             */
        }
        /**
         * 회원이 없으면, error 처리
         */
    }
}
