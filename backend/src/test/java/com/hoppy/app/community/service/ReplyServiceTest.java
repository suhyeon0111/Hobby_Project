package com.hoppy.app.community.service;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.domain.ReReply;
import com.hoppy.app.community.domain.Reply;
import com.hoppy.app.community.dto.CreateReReplyDto;
import com.hoppy.app.community.dto.CreateReplyDto;
import com.hoppy.app.community.repository.PostRepository;
import com.hoppy.app.community.repository.ReReplyRepository;
import com.hoppy.app.community.repository.ReplyRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.utility.Utility;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * packageName    : com.hoppy.app.community.service
 * fileName       : ReplyServiceTest
 * author         : Kim
 * date           : 2022-08-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-08-31        Kim       최초 생성
 */
@SpringBootTest
class ReplyServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    ReReplyRepository reReplyRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ReplyService replyService;

    @AfterEach
    void clean() {
        reReplyRepository.deleteAll();
        replyRepository.deleteAll();
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void createReply() {
        // given
        Member member = memberRepository.save(Utility.testMember(1L));
        Post post = postRepository.save(Utility.testPost(member));
        CreateReplyDto createReplyDto = CreateReplyDto.builder()
                .postId(post.getId())
                .content("content")
                .build();

        // when
        Reply reply = replyService.createReply(member.getId(), createReplyDto);

        // then
        assertThat(reply.getContent()).isEqualTo("content");
    }

    @Test
    void createReReply() {
        // given
        Member member = memberRepository.save(Utility.testMember(1L));
        Reply reply = replyRepository.save(Utility.testReply(member));
        CreateReReplyDto createReReplyDto = CreateReReplyDto.builder()
                .replyId(reply.getId())
                .content("content")
                .build();

        // when
        ReReply reReply = replyService.createReReply(member.getId(), createReReplyDto);

        // then
        assertThat(reReply.getContent()).isEqualTo("content");
    }
}