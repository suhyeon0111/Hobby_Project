package com.hoppy.app.member.service;


import static org.assertj.core.api.Assertions.*;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.dto.UpdateMemberDto;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
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

        assertThat(member.getUsername()).isEqualTo(dto.getUsername());
    }
    
    @Test
    @DisplayName("멤버 중복 검사 테스트")
    void checkUsernameDuplicate() {
        memberRepository.save(
                Member.builder().id(1L).username("대한쿼카").build()
        );
        memberRepository.save(
                Member.builder().id(2L).username("경태쿼카").build()
        );
        UpdateMemberDto dto = UpdateMemberDto.builder().username("대한쿼카").build();

        Throwable exception = org.junit.jupiter.api.Assertions.assertThrows(
                BusinessException.class, () -> {
                    memberService.updateById(2L, dto);
                }
        );
        org.junit.jupiter.api.Assertions.assertEquals(ErrorCode.MEMBER_DUPLICATE.getMessage(), exception.getMessage());
    }
}
