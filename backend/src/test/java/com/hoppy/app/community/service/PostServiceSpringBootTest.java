package com.hoppy.app.community.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.domain.ReReply;
import com.hoppy.app.community.domain.Reply;
import com.hoppy.app.community.dto.PostDetailDto;
import com.hoppy.app.community.dto.PostDto;
import com.hoppy.app.community.repository.PostRepository;
import com.hoppy.app.community.repository.ReReplyRepository;
import com.hoppy.app.community.repository.ReplyRepository;
import com.hoppy.app.like.domain.MemberPostLike;
import com.hoppy.app.like.repository.MemberPostLikeRepository;
import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.repository.MeetingRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 태경 2022-08-10
 */
@SpringBootTest
class PostServiceSpringBootTest {

    @Autowired
    PostService postService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    ReReplyRepository reReplyRepository;

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    MemberPostLikeRepository memberPostLikeRepository;

    @AfterEach
    void clean() {
        memberPostLikeRepository.deleteAll();
        reReplyRepository.deleteAll();
        replyRepository.deleteAll();
        postRepository.deleteAll();
        meetingRepository.deleteAll();
    }

    /*
     * 22.08.10
     * pagingPostList 통합 테스트 진행 성공 -tae
     * */
    @DisplayName("게시물 페이징 서비스 테스트")
    @Test
    void pagingPostListTest() {
        // given
        final long TEST_MEMBER_ID = 1L;
        final int POST_COUNT = 10;
        final int REPLY_COUNT = 10;
        final int RE_REPLY_COUNT = 5;
        final int PAGING_SIZE = 8;

        Member member = memberRepository.save(
                Member.builder()
                        .id(TEST_MEMBER_ID)
                        .build()
        );
        Meeting meeting = meetingRepository.save(
                Meeting.builder()
                        .ownerId(TEST_MEMBER_ID)
                        .title("title")
                        .content("content")
                        .category(Category.LIFE)
                        .memberLimit(10)
                        .build()
        );
        for(int i = 0; i < POST_COUNT; i++) {
            Post post = postRepository.save(
                Post.builder()
                        .title(i + "-title")
                        .content(i + "-content")
                        .author(member)
                        .meeting(meeting)
                        .build()
            );
            for(int j = 0; j < REPLY_COUNT; j++) {
                Reply reply = replyRepository.save(
                    Reply.builder()
                            .content(j + "-content")
                            .post(post)
                            .build()
                );
                for(int k = 0; k < RE_REPLY_COUNT; k++) {
                    reReplyRepository.save(
                        ReReply.builder()
                                .content(k + "-content")
                                .reply(reply)
                                .build()
                    );
                }
            }
            if(i % 2 == 0) continue;
            memberPostLikeRepository.save(
                    MemberPostLike.builder()
                            .member(member)
                            .post(post)
                            .build()
            );
        }

        // when
        List<PostDto> postDtoList = postService.pagingPostListV2(meeting, Long.MAX_VALUE, TEST_MEMBER_ID);

        /*
        * 발생 쿼리
        * - 최초 Post 페이징 쿼리: 1
        * - 멤버 조회 쿼리: 1
        * - 댓글, 대댓글 조회 쿼리: 2~4 (JPA 내부 최적화에 따름)
        * - 게시물 좋아요 조회 쿼리: 8
        * */

        // then
        assertThat(postDtoList.size()).isEqualTo(PAGING_SIZE);
        assertThat(postDtoList.get(0).getReplyCount()).isEqualTo(REPLY_COUNT + (REPLY_COUNT * RE_REPLY_COUNT));
    }

    @DisplayName("게시물 상세 조회 테스트")
    @Test
    void getPostDetailTest() {
        // given
        final long TEST_MEMBER_ID = 1L;
        final int REPLY_COUNT = 2;
        final int RE_REPLY_COUNT = 2;

        Member member = memberRepository.save(
                Member.builder()
                        .id(TEST_MEMBER_ID)
                        .build()
        );
        Meeting meeting = meetingRepository.save(
                Meeting.builder()
                        .ownerId(TEST_MEMBER_ID)
                        .title("title")
                        .content("content")
                        .category(Category.LIFE)
                        .memberLimit(10)
                        .build()
        );
        Post post = postRepository.save(
                Post.builder()
                        .title("0-title")
                        .content("0-content")
                        .author(member)
                        .meeting(meeting)
                        .build()
        );
        for(int j = 0; j < REPLY_COUNT; j++) {
            Reply reply = replyRepository.save(
                    Reply.builder()
                            .content(j + "-content")
                            .author(member)
                            .post(post)
                            .build()
            );
            for(int k = 0; k < RE_REPLY_COUNT; k++) {
                reReplyRepository.save(
                        ReReply.builder()
                                .content(k + "-content")
                                .author(member)
                                .reply(reply)
                                .build()
                );
            }
        }
        memberPostLikeRepository.save(
                MemberPostLike.builder()
                        .member(member)
                        .post(post)
                        .build()
        );

        // when
        // PostDetailDto postDetailDto = postService.getPostDetailV1(post.getId(), TEST_MEMBER_ID);
        PostDetailDto postDetailDto = postService.getPostDetailV2(post.getId(), TEST_MEMBER_ID);

        /*
        * 발생 쿼리
        * 1) 한 방 조회 쿼리
        * 2) 게시물에 좋아요 눌렀는지 확인 쿼리
        * 3) 게시물의 좋아요 수 조회 쿼리
        * 4) 각 댓글에 좋아요 눌렀는지 확인 쿼리
        * 5) 각 댓글의 좋아요 수 조회 쿼리
        * 6) 각 대댓글에 좋아요 눌렀는지 확인 쿼리
        * 7) 각 대댓글의 좋아요 수 조회 쿼리
        * */

        // then
        assertThat(postDetailDto.getId()).isEqualTo(post.getId());
        assertThat(postDetailDto.isLiked()).isTrue();
        assertThat(postDetailDto.getReplyCount()).isEqualTo(REPLY_COUNT + (REPLY_COUNT * RE_REPLY_COUNT));
    }
}