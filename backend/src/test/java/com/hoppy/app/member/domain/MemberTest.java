package com.hoppy.app.member.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.hoppy.app.member.repository.MemberMeetingRepository;
import com.hoppy.app.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberTest {

    @Autowired
    MemberMeetingRepository memberMeetingRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void addMyMeetings() {
        Member member = Member.builder().id(1234L).build();
        System.out.println("member.getMyMeetings() = " + member.getMyMeetings());
    }
}