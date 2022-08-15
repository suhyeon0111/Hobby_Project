package com.hoppy.app.community.service;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.repository.PostRepository;
import com.hoppy.app.like.repository.MemberPostLikeRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 태경 2022-08-15
 */
@SpringBootTest
public class PostServiceLikeConcurrencyTest {

    @Autowired
    MemberPostLikeRepository memberPostLikeRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Autowired
    EntityManager em;

    Logger log = (Logger) LoggerFactory.getLogger(PostServiceLikeConcurrencyTest.class);

    @AfterEach
    void clean() {
        memberPostLikeRepository.deleteAll();
        memberRepository.deleteAll();
        postRepository.deleteAll();
    }

    @DisplayName("게시물 좋아요 동시성 중복 요청 처리 테스트")
    @Test
    void likeTest() throws InterruptedException {
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

        final int REQUEST_COUNT = 10;
        CountDownLatch countDownLatch = new CountDownLatch(REQUEST_COUNT);
        List<ParticipateWorker> workers = Stream
                .generate(() -> new ParticipateWorker(member.getId(), post.getId(), countDownLatch))
                .limit(REQUEST_COUNT)
                .collect(Collectors.toList());

        // when
        workers.forEach(W -> new Thread(W).start());
        countDownLatch.await();

        // then
        Assertions.assertThat(memberPostLikeRepository.findAll().size()).isEqualTo(1);
    }

    private class ParticipateWorker implements Runnable {

        private final long memberId;
        private final long postId;
        private final CountDownLatch countDownLatch;

        public ParticipateWorker(long memberId, long postId, CountDownLatch countDownLatch) {
            this.memberId = memberId;
            this.postId = postId;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                postService.likePost(memberId, postId);
            }
            catch (Exception e) {
                log.warn("*** [" + e.getClass().getSimpleName() + "]: " + e.getMessage());
            }
            finally {
                countDownLatch.countDown();
            }
        }
    }
}
