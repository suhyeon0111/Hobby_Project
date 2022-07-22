package com.hoppy.app.like.repository;

import com.hoppy.app.like.domain.LikeManager;
import com.hoppy.app.like.domain.MemberMeetingLike;
import java.util.Optional;
import javax.persistence.EntityManager;
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
class MemberMeetingLikeRepositoryTest {

    @Autowired
    LikeManagerRepository likeManagerRepository;

    @Autowired
    MemberMeetingLikeRepository memberMeetingLikeRepository;

    @BeforeAll
    void before() {

    }

    @Test
    void findMemberMeetingLikeByMemberLikeAndMeetingId() {

        // given
        Long meetingId = 3L;
        LikeManager likeManager = likeManagerRepository.save(LikeManager.builder().build());
        MemberMeetingLike memberMeetingLike = MemberMeetingLike.builder()
                .meetingId(meetingId)
                .likeManager(likeManager)
                .build();
        memberMeetingLikeRepository.save(memberMeetingLike);

        // when
        Optional<MemberMeetingLike> meetingLikeOptional = memberMeetingLikeRepository.findMemberMeetingLikeByLikeManagerAndMeetingId(
                likeManager, meetingId);

        // then
        Assertions.assertThat(meetingLikeOptional).isPresent();
    }
}