package com.hoppy.app.like.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.repository.PostRepository;
import com.hoppy.app.community.service.PostService;
import com.hoppy.app.like.domain.MemberPostLike;
import com.hoppy.app.like.repository.MemberPostLikeRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author 태경 2022-08-07
 */
@SpringBootTest
class LikeServiceImplTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberPostLikeRepository memberPostLikeRepository;

    @Autowired
    LikeService likeService;

    @AfterEach
    void afterEach() {
        memberPostLikeRepository.deleteAll();
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void getPostLikesTest() {
        // given
        final var MEMBER_ID = 1L;
        Member member = memberRepository.save(
                Member.builder()
                        .id(MEMBER_ID)
                        .build()
        );
        final var POST_COUNT = 10;
        for(int i = 0; i < POST_COUNT; i++) {
            Post post = postRepository.save(
                    Post.builder()
                            .title((i + 1) + "-title")
                            .content((i + 1) + "-content")
                            .owner(member)
                            .build()
            );
            if((i + 1) % 2 == 0) {
                memberPostLikeRepository.save(
                        MemberPostLike.builder()
                                .memberId(member.getId())
                                .postId(post.getId())
                                .build()
                );
            }
        }
        // when
        List<Long> likes = likeService.getPostLikes(MEMBER_ID);

        // then
        assertThat(likes.size()).isEqualTo(5);
    }
}