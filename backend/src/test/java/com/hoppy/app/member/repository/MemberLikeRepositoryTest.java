package com.hoppy.app.member.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberLike;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @author 태경 2022-07-22
 */
@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
class MemberLikeRepositoryTest {

    @Autowired
    MemberLikeRepository memberLikeRepository;

    @Autowired
    MemberRepository memberRepository;

    @BeforeAll
    void before() {
        MemberLike memberLike = MemberLike.builder().build();
        memberLike = memberLikeRepository.save(memberLike);

        Member member = Member.builder().id(1L).build();
        member.setMemberLike(memberLike);
        memberRepository.save(member);
    }

    @Test
    void saveTestWithMember() {
        Optional<Member> optionalMember = memberRepository.findById(1L);
        Assertions.assertThat(optionalMember.isPresent()).isTrue();

        Optional<MemberLike> memberLike = memberLikeRepository.findMemberLikeByMemberWithMeetingLikes(optionalMember.get());
        Assertions.assertThat(memberLike.isPresent()).isTrue();
    }
}