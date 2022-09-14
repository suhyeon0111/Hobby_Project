package com.hoppy.app.login.auth.service;

import com.hoppy.app.login.WithMockCustomUser;
import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.web.WebAppConfiguration;



@SpringBootTest
@WebAppConfiguration
public class CustomOAuth2UserServiceTest {


    @Autowired
    CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setup() {
        memberRepository.save(Member.builder().socialType(SocialType.KAKAO).id(1234L)
                .profileImageUrl("www.test.com").email("demo123@naver.com").role(Role.USER).username("choidaehan").build());
    }

    @Test
    @WithMockCustomUser(id = "123", password = "secret", role = Role.GUEST, socialType = SocialType.KAKAO)
    void test() {
        customOAuth2UserService.print();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication.getName() = " + authentication.getName());
        Optional<Member> member = memberRepository.findById(1234L);
        System.out.println("member.get().getEmail() = " + member.get().getEmail());
    }

}
