package com.hoppy.app.community.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.repository.MeetingRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

/**
 * @author 태경 2022-08-03
 */
@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    MeetingRepository meetingRepository;

    @BeforeAll
    void beforeAll() {

    }

    @Transactional
    @AfterAll
    void after() {
        meetingRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    void infiniteScrollPagingPost() {
        // given
        /*
        * 관계를 맺기 위한 모임 생성
        * */
        Meeting meeting = meetingRepository.save(Meeting.builder()
                .ownerId(0L)
                .url("none")
                .title("테스트 모임")
                .content("테스트 모임")
                .category(Category.HEALTH)
                .memberLimit(10)
                .build()
        );

        /*
         * 20개의 게시물을 생성한다.
         * */
        for(int i = 0; i < 20; i++) {
            postRepository.save(Post.builder()
                    .title(i + ": title")
                    .content(i + ": content")
                    .meeting(meeting)
                    .build()
            );
        }

        // when
        List<Post> posts = postRepository.infiniteScrollPagingPost(meeting, Long.MAX_VALUE, PageRequest.of(0, 8));

        // then
        Assertions.assertThat(posts.size()).isEqualTo(8);
    }
}