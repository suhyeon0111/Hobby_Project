package com.hoppy.app.mypage.controller;

import com.hoppy.app.login.oauth.SocialType;
import com.hoppy.app.login.oauth.provider.AuthTokenProvider;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
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
    
    @GetMapping("/mypage")
    public void getUserSocialId(@RequestParam("jwt") String code) {
        String socialPk = authTokenProvider.getUserPk(code);
        Optional<Member> member = memberRepository.findBySocialTypeAndSocialId(SocialType.KAKAO, socialPk);
        System.out.println("member.get().getUsername() = " + member.get().getUsername());
    }
}
