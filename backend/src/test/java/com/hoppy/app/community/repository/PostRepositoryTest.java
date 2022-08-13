package com.hoppy.app.community.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.domain.ReReply;
import com.hoppy.app.community.domain.Reply;
import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.repository.MeetingRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author 태경 2022-08-06
 */
@DataJpaTest
// 외부 DB 사용할 때 주석 해제
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@ActiveProfiles("test")
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    ReReplyRepository reReplyRepository;

    @Autowired
    EntityManager em;

    @DisplayName("커뮤니티 게시물 페이지네이션 테스트")
    @Test
    void pagingPostListTest() {
        // given
        Meeting meeting = meetingRepository.save(
                Meeting.builder()
                        .ownerId(1L)
                        .title("0-title")
                        .content("0-content")
                        .category(Category.LIFE)
                        .memberLimit(10)
                        .build()
        );

        final int POST_COUNT = 10;
        final int REPLY_COUNT = 20;

        for(int i = 1; i <= POST_COUNT; i++) {
            Member member = memberRepository.save(
                    Member.builder()
                            .id((long) i)
                            .username(i + "-name")
                            .build()
            );
            Post post = postRepository.save(
                    Post.builder()
                            .title(i + "-title")
                            .content(i + "-content")
                            .meeting(meeting)
                            .author(member)
                            .build()
            );
            for(int j = 0; j < REPLY_COUNT; j++) {
                replyRepository.save(
                    Reply.builder()
                            .post(post)
                            .content(j + "-content")
                            .build()
                );
            }
        }
        em.flush();
        em.clear();

        // when
        final int PAGING_SIZE = 8;
        List<Post> posts = postRepository.infiniteScrollPagingPost(meeting, Long.MAX_VALUE, PageRequest.of(0, PAGING_SIZE));

        for (var p : posts) {
            var o = p.getAuthor();
            var replies = p.getReplies();

            int sum = 0;
            for(var r : replies) {
                sum += r.getReReplies().size();
            }

            System.out.println(
                    "[" + o.getId() + "]: "
                    + o.getUsername()
                    + " >> replies: "
                    + replies.size()
                    + " >> reReplies: "
                    + sum
            );
        }

        // then
        assertThat(posts.size()).isEqualTo(PAGING_SIZE);
    }

    @DisplayName("게시물 상세 조회 쿼리 테스트")
    @Test
    void getPostDetailTest() {
        // given
        long MEMBER_ID_COUNT = 1L;
        Member postAuthor = memberRepository.save(
                Member.builder()
                        .id(MEMBER_ID_COUNT++)
                        .build()
        );
        Post post = postRepository.save(
                Post.builder()
                        .title("test-title")
                        .content("test-content")
                        .author(postAuthor)
                        .build()
        );

        final int REPLY_COUNT = 20;
        final int RE_REPLY_COUNT = 5;

        for(int i = 0; i < REPLY_COUNT; i++) {
            Member replyAuthor = memberRepository.save(
                    Member.builder()
                            .id(MEMBER_ID_COUNT++)
                            .build()
            );
            Reply reply = replyRepository.save(
                    Reply.builder()
                            .author(replyAuthor)
                            .content(i + "-content")
                            .post(post)
                            .build()
            );

            for(int j = 0; j < RE_REPLY_COUNT; j++) {
                Member reReplyAuthor = memberRepository.save(
                        Member.builder()
                                .id(MEMBER_ID_COUNT++)
                                .build()
                );
                ReReply reReply = reReplyRepository.save(
                        ReReply.builder()
                                .author(reReplyAuthor)
                                .content(i + "-content")
                                .reply(reply)
                                .build()
                );
            }
        }

        em.flush();
        em.clear();

        // when
        Optional<Post> optionalPost = postRepository.getPostDetail(post.getId());
        assertThat(optionalPost).isPresent();
        post = optionalPost.get();

        // then
        assertThat(post.getReplies().size()).isEqualTo(REPLY_COUNT);
        post.getReplies().forEach(R -> assertThat(R.getReReplies().size()).isEqualTo(RE_REPLY_COUNT));
    }
}