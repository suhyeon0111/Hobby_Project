package com.hoppy.app.community.controller;

import com.hoppy.app.community.domain.ReReply;
import com.hoppy.app.community.domain.Reply;
import com.hoppy.app.community.repository.ReReplyRepository;
import com.hoppy.app.community.repository.ReplyRepository;
import com.hoppy.app.like.domain.MemberReReplyLike;
import com.hoppy.app.like.domain.MemberReplyLike;
import com.hoppy.app.like.repository.MemberReReplyLikeRepository;
import com.hoppy.app.like.repository.MemberReplyLikeRepository;
import com.hoppy.app.login.WithMockCustomUser;
import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName    : com.hoppy.app.community.controller
 * fileName       : ReplyControllerTest
 * author         : Kim
 * date           : 2022-08-29
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-08-29        Kim       최초 생성
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@DisplayName("댓글 컨트롤러 테스트")
class ReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReReplyRepository reReplyRepository;

    @Autowired
    private MemberReplyLikeRepository memberReplyLikeRepository;

    @Autowired
    private MemberReReplyLikeRepository memberReReplyLikeRepository;

    @AfterEach
    void after() {
        memberReReplyLikeRepository.deleteAll();
        memberReplyLikeRepository.deleteAll();
        reReplyRepository.deleteAll();
        replyRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @WithMockCustomUser(id = "1", password = "secret-key", role = Role.USER, socialType = SocialType.KAKAO)
    void likeReply() throws Exception {
        // given
        final long TEST_MEMBER_ID = 1L;
        Member member = memberRepository.save(
            Member.builder()
                .id(TEST_MEMBER_ID)
                .username("testName")
                .profileImageUrl("testProfileUrl")
                .build()
        );
        Reply reply = replyRepository.save(
            Reply.builder()
                .content("content")
                .build()
        );

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/reply/like/" + reply.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("reply-like-request",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andDo(print());

        // then
        Optional<MemberReplyLike> opt = memberReplyLikeRepository.findByMemberIdAndReplyId(member.getId(), reply.getId());
        assertThat(opt).isPresent();
    }

    @Test
    @WithMockCustomUser(id = "1", password = "secret-key", role = Role.USER, socialType = SocialType.KAKAO)
    void dislikeReply() throws Exception {
        // given
        final long TEST_MEMBER_ID = 1L;
        Member member = memberRepository.save(
                Member.builder()
                        .id(TEST_MEMBER_ID)
                        .username("testName")
                        .profileImageUrl("testProfileUrl")
                        .build()
        );
        Reply reply = replyRepository.save(
                Reply.builder()
                        .content("content")
                        .build()
        );
        memberReplyLikeRepository.save(MemberReplyLike.of(member, reply));

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reply/dislike/" + reply.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("reply-dislike-request",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andDo(print());

        // then
        Optional<MemberReplyLike> opt = memberReplyLikeRepository.findByMemberIdAndReplyId(member.getId(), reply.getId());
        assertThat(opt).isEmpty();
    }

    @Test
    @WithMockCustomUser(id = "1", password = "secret-key", role = Role.USER, socialType = SocialType.KAKAO)
    void likeReReply() throws Exception {
        // given
        final long TEST_MEMBER_ID = 1L;
        Member member = memberRepository.save(
                Member.builder()
                        .id(TEST_MEMBER_ID)
                        .username("testName")
                        .profileImageUrl("testProfileUrl")
                        .build()
        );
        ReReply reReply = reReplyRepository.save(
                ReReply.builder()
                        .content("content")
                        .build()
        );

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/reply/re/like/" + reReply.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("reReply-like-request",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andDo(print());

        // then
        Optional<MemberReReplyLike> opt = memberReReplyLikeRepository.findByMemberIdAndReplyId(member.getId(), reReply.getId());
        assertThat(opt).isPresent();
    }

    @Test
    @WithMockCustomUser(id = "1", password = "secret-key", role = Role.USER, socialType = SocialType.KAKAO)
    void dislikeReReply() throws Exception {
        // given
        final long TEST_MEMBER_ID = 1L;
        Member member = memberRepository.save(
                Member.builder()
                        .id(TEST_MEMBER_ID)
                        .username("testName")
                        .profileImageUrl("testProfileUrl")
                        .build()
        );
        ReReply reReply = reReplyRepository.save(
                ReReply.builder()
                        .content("content")
                        .build()
        );
        memberReReplyLikeRepository.save(MemberReReplyLike.of(member, reReply));

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reply/re/dislike/" + reReply.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("reReply-dislike-request",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andDo(print());

        // then
        Optional<MemberReReplyLike> opt = memberReReplyLikeRepository.findByMemberIdAndReplyId(member.getId(), reReply.getId());
        assertThat(opt).isEmpty();
    }
}