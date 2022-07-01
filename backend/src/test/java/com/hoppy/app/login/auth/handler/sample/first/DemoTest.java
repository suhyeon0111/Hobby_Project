package com.hoppy.app.login.auth.handler.sample.first;

import com.hoppy.app.login.auth.authentication.OAuth2UserDetails;
import com.hoppy.app.login.auth.handler.OAuth2AuthenticationSuccessHandler;
import com.hoppy.app.login.auth.handler.sample.first.service.DemoService;
import com.hoppy.app.login.auth.handler.sample.first.service.TestUserDetailsService;
import com.hoppy.app.login.auth.service.LoadUserService;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.repository.MemberRepository;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

@SpringBootTest
@WithMockUser
@TestInstance(Lifecycle.PER_CLASS)
@Transactional
public class DemoTest {

    OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    
    @Autowired
    public MemberRepository memberRepository;

    TestUserDetailsService testUserDetailsService;
    LoadUserService loadUserService;
    CustomUser customUser;
    DemoService demoService;
    OAuth2UserDetails oAuth2UserDetails;
    @Test
    void test1() {
        System.out.println("test1: " + SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @WithAnonymousUser
    void test2() {
        System.out.println("test2: " + SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @WithUserDetails(value = "name", userDetailsServiceBeanName = "testUserDetailsService")
    void test3() {
//        testUserDetailsService.print();
        demoService.print();
    }


    @Test
    @WithMockCustomUser(username = "myName", role = Role.USER)
    void test4() {
        demoService.print();
    }

    @Test
    @WithMockCustomUser(role =  Role.USER)
    @DisplayName("WithMockCustomUser Test")
    void test6() {

    }


}
