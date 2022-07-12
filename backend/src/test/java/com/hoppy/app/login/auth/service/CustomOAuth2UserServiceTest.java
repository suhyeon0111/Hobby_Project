package com.hoppy.app.login.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.hoppy.app.login.WithMockCustomUser;
import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;



@SpringBootTest
//@WithMockUser
//@RunWith(SpringRunner.class)
@WebAppConfiguration
public class CustomOAuth2UserServiceTest {


    @Autowired
    CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TestUserDetailsService userDetailsService;

    @Test
    void save() {
    }

//    @Before
//    Member setup() {
//        return Member.builder().username("강해상")
//                .role(Role.USER).email("test123@naver.com").socialId("333444555").profileImageUrl("www.where.com").socialType(
//                        SocialType.KAKAO).build();
//    }

    @Test
    @WithMockUser
    public void authenticatedUser() {
//        Member member = setup();
//        String socialId = customOAuth2UserService.saveMember(member);
//        System.out.println(socialId);
//        System.out.println("member.getUsername() = " + member.getUsername());
//        System.out.println("member.getSocialId() = " + member.getSocialId());
//        assertThat(customOAuth2UserService.saveMember(member)).isNotNull();
//        assertThat(customOAuth2UserService.saveMember(new Member())).isNotNull();
//        assertThat(customOAuth2UserService.saveMember(new Member())).isNotNull();
    }

    @BeforeEach
    void setup() {
        memberRepository.save(Member.builder().socialType(SocialType.KAKAO).id(1234L)
                .profileImageUrl("www.test.com").email("demo123@naver.com").role(Role.USER).username("choidaehan").build());
    }

    @Test
    @WithMockCustomUser(id = "123", password = "secret", role = Role.GUEST, socialType = SocialType.KAKAO)
    void test() {
        customOAuth2UserService.print();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        userDetailsService.print();
    }

//    @Test
//    @WithUserDetails("name")
//    void test2() {
//        userDetailsService.print();
//    }
}
