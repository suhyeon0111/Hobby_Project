package com.hoppy.app.community.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.repository.PostRepository;
import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.repository.MeetingRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

/**
 * @author 태경 2022-08-06
 */
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MeetingRepository meetingRepository;

    @AfterEach
    void afterEach() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
        meetingRepository.deleteAll();
    }

    @DisplayName("커뮤니티 게시물 페이지네이션 테스트")
    @Test
    void pagingPostListTest() {
        // given
        Member member = memberRepository.save(
                Member.builder()
                        .id(1L)
                        .username("test-name")
                        .build()
        );
        Meeting meeting = meetingRepository.save(
                Meeting.builder()
                        .ownerId(member.getId())
                        .title("test-title")
                        .content("test-content")
                        .category(Category.LIFE)
                        .memberLimit(10)
                        .build()
        );
        final int POST_COUNT = 10;
        for(int i = 0; i < POST_COUNT; i++) {
            postRepository.save(
                    Post.builder()
                            .title((i + 1) + "-title")
                            .content((i + 1) + "-content")
                            .owner(member)
                            .meeting(meeting)
                            .build()
            );
        }

        // when
        final int PAGING_SIZE = 8;
        var posts = postRepository.infiniteScrollPagingPost(meeting, Long.MAX_VALUE, PageRequest.of(0, PAGING_SIZE));

        // then
        assertThat(posts.size()).isEqualTo(PAGING_SIZE);
    }
}