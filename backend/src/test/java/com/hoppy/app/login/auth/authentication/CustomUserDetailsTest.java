package com.hoppy.app.login.auth.authentication;

import static org.junit.jupiter.api.Assertions.*;

import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

class CustomUserDetailsTest {

    CustomUserDetails create(Member member) {
        return CustomUserDetails.builder()
                .id(member.getId())
                .password(member.getPassword())
                .socialType(member.getSocialType())
                .role(member.getRole())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(Role.USER.toString())))
                .build();
    }

    @Test
    void testCreate() {
        Long testId = 9876543210L;

        Member member = Member.builder()
                .id(testId)
                .password("")
                .socialType(SocialType.KAKAO)
                .role(Role.USER).build();

        CustomUserDetails customUserDetails = create(member);

        Assertions.assertThat(customUserDetails.getName()).isEqualTo("9876543210");
        Assertions.assertThat(customUserDetails.getRole()).isEqualTo(Role.USER);
    }
}