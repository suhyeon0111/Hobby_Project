package com.hoppy.app.mypage.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoppy.app.login.WithMockCustomUser;
import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.dto.LoginMemberDto;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.member.service.MemberDTOService;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 오직 Controller에 대해서만 테스트를 검증할 때는 @WebMvcTest를 사용하면 되지만,
 * @Service, @Repository, JPA 등을 같이 사용해야 하는 로직은 @SpringBootTest & @AutoConfigureMockMvc 사용
 */

@SpringBootTest
@AutoConfigureMockMvc
class MyPageControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberDTOService memberDTOService;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    /**
     * 이미 가입돼있는 가상의 회원
     */
    @BeforeEach
    void makeVirtualMember() {
        memberRepository.save(
                Member.builder()
                        .id(1L)
                        .socialType(SocialType.KAKAO).socialId("12341234")
                        .username("마석도").email("test123@naver.com").password("98765")
                        .profileImageUrl("https://www.xxx.com//myimage").role(Role.USER).intro("반갑습니다!").build()
        );
    }

    /**
     * 성공 케이스
     */
    @Test
    @WithMockCustomUser(username = "1234L", password = "98765", role = Role.USER, socialType = SocialType.KAKAO)
    void showMemberPageSuccess() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String socialId = authentication.getName();
        Optional<Member> member = memberRepository.findBySocialId(socialId);
        if(member.isPresent()) {
            LoginMemberDto memberDto = memberDTOService.loginSuccessResponse(member.get());
        } else {
            System.out.println("존재하지 않는 회원입니다.");
        }
    }

    /**
     * 실패 케이스 (makeVirtualMember()에서 설정한 socialId와 다른 MockCustomUser 선언)
     */
    @Test
    @WithMockCustomUser(username = "5678L", password = "98765", role = Role.USER, socialType = SocialType.KAKAO)
    void showMemberPageFailure() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String socialId = authentication.getName();
        Optional<Member> member = memberRepository.findBySocialId(socialId);
        if(member.isPresent()) {
            LoginMemberDto memberDto = memberDTOService.loginSuccessResponse(member.get());
        } else {
            System.out.println("존재하지 않는 회원입니다.");
        }
    }



    @Test
    void updateMember() {
    }
}