package com.hoppy.app.community.service;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.repository.PostRepository;
import com.hoppy.app.like.domain.MemberPostLike;
import com.hoppy.app.like.repository.MemberPostLikeRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @author 태경 2022-08-14
 */
@DataJpaTest
@DisplayName("게시물 좋아요 기능 DataJpaTest")
public class PostServiceLikeFeatureTest {

    @Autowired
    MemberPostLikeRepository memberPostLikeRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    EntityManager em;

    @Test
    void likeTest_좋아요유니크속성테스트() {
        // given
        Member member = memberRepository.save(Member.builder()
                .id(1L)
                .build()
        );
        Post post = postRepository.save(Post.builder()
                .title("test-title")
                .content("test-content")
                .build()
        );
        memberPostLikeRepository.save(MemberPostLike.of(member, post));
        em.flush();
        em.clear();

        // when
        Assertions.assertThrows(RuntimeException.class, () -> memberPostLikeRepository.save(MemberPostLike.of(member, post)));
    }
}
