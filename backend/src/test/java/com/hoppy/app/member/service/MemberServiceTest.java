package com.hoppy.app.member.service;


import static org.assertj.core.api.Assertions.*;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.dto.UpdateMemberDto;
import com.hoppy.app.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("멤버 수정 테스트")
    void updateMember() {
        memberRepository.save(Member.builder()
                 .id(1L).username("최대한").profileImageUrl("xxx").email("naver.com").intro("hello").build());
        
        UpdateMemberDto dto = UpdateMemberDto.builder().username("김경태").build();
        
        Member member = memberService.updateById(1L, dto);
        
//        Member member = memberService.findById(1L);
        System.out.println("member.getUsername() = " + member.getUsername());
        System.out.println("member.getProfileImageUrl() = " + member.getProfileImageUrl());
        System.out.println("member.getIntro() = " + member.getIntro());

        System.out.println("member.getEmail() = " + member.getEmail());
        assertThat(member.getUsername()).isEqualTo(dto.getUsername());
    }
}
