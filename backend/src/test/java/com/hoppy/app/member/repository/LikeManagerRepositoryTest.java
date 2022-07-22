package com.hoppy.app.member.repository;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.like.domain.LikeManager;
import com.hoppy.app.like.repository.LikeManagerRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
class LikeManagerRepositoryTest {

    @Autowired
    LikeManagerRepository likeManagerRepository;

    @Autowired
    MemberRepository memberRepository;

    @BeforeAll
    void before() {
        LikeManager likeManager = LikeManager.builder().build();
        likeManager = likeManagerRepository.save(likeManager);

        Member member = Member.builder().id(1L).build();
        member.setLikeManager(likeManager);
        memberRepository.save(member);
    }

    @Test
    void saveTestWithMember() {
        Optional<Member> optionalMember = memberRepository.findById(1L);
        Assertions.assertThat(optionalMember.isPresent()).isTrue();

        Optional<LikeManager> memberLike = likeManagerRepository.findMemberLikeAndMeetingLikesByMember(optionalMember.get());
        Assertions.assertThat(memberLike.isPresent()).isTrue();
    }
}