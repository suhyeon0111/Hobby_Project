package com.hoppy.app.mypage.controller;

import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.dto.LoginMemberDto;
import com.hoppy.app.mypage.dto.MyPageMemberDto;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.mypage.service.UpdateMemberServiceImpl;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {

    private final MemberRepository memberRepository;
    private final UpdateMemberServiceImpl updateMemberServiceImpl;

    /**
     * 모든 api 요청 메소드에서 Principal을 인자로 설정해두면, 현재 인증된 사용자 정보를 가져올 수 있음.
     * 이를 이용해 사용자 고유 식별값 (socialId) 추출 가능.
     */

    /**
     * 현재 로그인 한 사용자 정보를 반환
     */
    @GetMapping("/show")
    public LoginMemberDto showMemberPage(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getId();
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isPresent()) {
            LoginMemberDto memberDto = LoginMemberDto.of(member.get());
            return memberDto;
        } else {
            System.out.println("존재하지 않는 회원입니다.");
        }
        return null;
    }

    /**
     * 'PUT' 요청으로 사용자 이름, 프로필 이미지, 소개글을 파라미터로 받아 멤버 정보 수정.
     * 수정된 내용을 응답으로 보냄. (return 타입 void로 설정해도 상관 없을 듯)
     */
    @PutMapping("/update")
    public MyPageMemberDto updateMember(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("username") String username,
            @RequestParam("profileImageUrl") String profileImageUrl,
            @RequestParam("intro") String intro
    ) {
        Long memberId = userDetails.getId();
        Member member = updateMemberServiceImpl.updateMember(memberId, username, profileImageUrl, intro);
        return MyPageMemberDto.builder().username(member.getUsername()).profileUrl(member.getProfileImageUrl()).intro(member.getIntro()).build();
    }
}
