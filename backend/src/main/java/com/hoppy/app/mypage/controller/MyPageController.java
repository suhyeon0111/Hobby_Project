package com.hoppy.app.mypage.controller;

import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.login.auth.authentication.OAuth2UserDetails;
import com.hoppy.app.login.auth.provider.AuthTokenProvider;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.member.service.MemberServiceImpl;
import java.util.HashMap;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MyPageController {
    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberRepository;

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
    }

}
