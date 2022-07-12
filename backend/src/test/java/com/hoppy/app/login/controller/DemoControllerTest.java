package com.hoppy.app.login.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.hoppy.app.login.WithMockCustomUser;
import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs
class DemoControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("현재 인증된 사용자의 SocialId로 정보 조회")
    @WithMockCustomUser(id = "98765", password = "secret", role = Role.USER, socialType = SocialType.KAKAO)
    void login() throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        MemberRepository mr = mock(MemberRepository.class);

        Member member = Member.builder()
                .id(12341234L)
                .username("마석도")
                .email("test123@naver.com")
                .role(Role.USER)
                .socialType(SocialType.KAKAO)
                .profileImageUrl("www.image.com/myimage")
                .id(1L)
                .intro("반갑습니다. 잘부탁드립니다!")
                .password("9999")
                .build();

        when(mr.save(member)).thenReturn(new Member(1234L, "email", "testUser", "profileUrl",
                "hello", SocialType.KAKAO, Role.USER));
        when(mr.findAll()).thenReturn(Mockito.anyList());

        System.out.println("member.getUsername() = " + member.getUsername());  // "마석도"

        System.out.println("mr.save(member).getUsername() = " + mr.save(member).getUsername());  // "testUser"
        System.out.println("mr.findAll() = " + mr.findAll());  // []

        assertFalse(mr.save(member) == member);
        assertFalse(mr.findAll() == new Object());
    }

}