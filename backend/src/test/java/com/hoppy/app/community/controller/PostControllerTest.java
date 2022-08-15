package com.hoppy.app.community.controller;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.domain.ReReply;
import com.hoppy.app.community.domain.Reply;
import com.hoppy.app.community.repository.PostRepository;
import com.hoppy.app.community.repository.ReReplyRepository;
import com.hoppy.app.community.repository.ReplyRepository;
import com.hoppy.app.like.domain.MemberPostLike;
import com.hoppy.app.like.repository.MemberPostLikeRepository;
import com.hoppy.app.login.WithMockCustomUser;
import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.repository.MeetingRepository;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

/**
 * @author 태경 2022-08-14
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestInstance(Lifecycle.PER_METHOD)
@DisplayName("Post 컨트롤러 테스트")
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

    final long TEST_MEMBER_ID = 1L;
    final int REPLY_COUNT = 2;
    final int RE_REPLY_COUNT = 2;

    @AfterEach
    void after() {
        memberPostLikeRepository.deleteAll();
        reReplyRepository.deleteAll();
        replyRepository.deleteAll();
        postRepository.deleteAll();
        memberRepository.deleteAll();
        meetingRepository.deleteAll();
    }

    @Test
    @WithMockCustomUser(id = "1", password = "pass-word", role = Role.USER, socialType = SocialType.KAKAO)
    void getPostDetail() throws Exception {
        // given
        Member member = memberRepository.save(
                Member.builder()
                        .id(TEST_MEMBER_ID)
                        .username("testName")
                        .profileImageUrl("testProfileUrl")
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

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/post/" + post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("message", is("게시물 조회 완료")))
                .andExpect(jsonPath("$.data.authorName", is("testName")))
                .andExpect(jsonPath("$.data.authorProfileUrl", is("testProfileUrl")))
                .andExpect(jsonPath("$.data.replyCount", is(REPLY_COUNT + (REPLY_COUNT * RE_REPLY_COUNT))))
                .andDo(document("post-detail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andDo(print());
    }

    @Test
    @WithMockCustomUser(id = "1", password = "pass-word", role = Role.USER, socialType = SocialType.KAKAO)
    void likePostTest() throws Exception {
        // given
        Member member = memberRepository.save(
                Member.builder()
                        .id(TEST_MEMBER_ID)
                        .username("test-name")
                        .profileImageUrl("test-url")
                        .build()
        );
        Post post = postRepository.save(
                Post.builder()
                        .title("test-title")
                        .content("test-content")
                        .build()
        );

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/post/like/" + post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("post-like-request",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andDo(print());
    }
}