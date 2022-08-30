package com.hoppy.app.story.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoppy.app.like.repository.MemberStoryLikeRepository;
import com.hoppy.app.login.WithMockCustomUser;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.dto.StoryDetailDto;
import com.hoppy.app.story.dto.StoryReplyRequestDto;
import com.hoppy.app.story.repository.StoryReplyRepository;
import com.hoppy.app.story.repository.StoryRepository;
import com.hoppy.app.story.service.StoryService;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.print.attribute.standard.Media;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class StoryLikeAndReplyControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    StoryReplyRepository storyReplyRepository;

    @Autowired
    MemberStoryLikeRepository memberStoryLikeRepository;

    @Autowired
    StoryRepository storyRepository;

    @Autowired
    StoryService storyService;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {

        Member member1 = Member.builder().id(8669L).username("최대한").profileImageUrl("korea88@naver.com").build();
        Member member2 = Member.builder().id(7601L).username("김태경").profileImageUrl("seaworld@daum.net").build();

        memberRepository.save(member1);
        memberRepository.save(member2);

        for(int i = 1; i <= 5; i++) {
            Member member = null;
            if(i % 2 == 0) {
                member = member1;
            } else {
                member = member2;
            }
            Story story = storyRepository.save(
                    Story.builder()
                            .member(member)
                            .title(i+"th Story")
                            .content("This is " + i + "th Story")
                            .filePath(i+".jpg")
                            .member(member).build()
            );
            storyService.likeStory(member2.getId(), story.getId());
        }
    }

    @AfterEach
    void afterEach() {
        memberStoryLikeRepository.deleteAll();
        storyRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("스토리 좋아요 컨트롤러 테스트")
    @Test
    @WithMockCustomUser(id = "8669")
    void storyLikeTest() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Story> storyList = storyRepository.findAll();
        Long storyId = storyList.get(0).getId();
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .get("/story/like")
                .param("id", String.valueOf(storyId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        );
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("story-like-request",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @DisplayName("좋아요 취소 컨트롤러 테스트")
    @Test
    @WithMockCustomUser(id = "7601")
    void storyDislikeTest() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Story> storyList = storyRepository.findAll();
        Long storyId = storyList.get(0).getId();
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .delete("/story/like")
                .param("id", String.valueOf(storyId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        );
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("story-dislike-request",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @DisplayName("스토리 댓글 등록 테스트")
    @Test
    @WithMockCustomUser(id = "8669")
    void uploadStoryReplyTest() throws Exception {
        List<Story> storyList = storyRepository.findAll();
        Long storyId = storyList.get(0).getId();
        StoryReplyRequestDto dto = StoryReplyRequestDto.builder().content("This is Story Reply Test").build();
        String content = objectMapper.writeValueAsString(dto);
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post("/story/reply")
                .param("id", String.valueOf(storyId))
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        );
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("story-reply-request",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @DisplayName("스토리 댓글 삭제 테스트")
    @Test
    @WithMockCustomUser(id = "8669")
    void deleteStoryReplyTest() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Long memberId = Long.parseLong(authentication.getName());
        Member member = memberService.findById(memberId);

        List<Story> storyList = storyRepository.findAll();
        Long storyId = storyList.get(0).getId();

        Story story = storyService.findByStoryId(storyId);

        for(int i = 0; i < 3; i++) {
            String content = "This is " + String.valueOf(i+1) + "th reply";
            StoryReplyRequestDto dto = StoryReplyRequestDto.builder()
                    .content(content)
                    .member(member)
                    .story(story)
                    .build();

            storyService.uploadStoryReply(memberId, storyId, dto);
        }

        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .delete("/story/reply")
                .param("storyId", String.valueOf(storyId))
                .param("replyId", String.valueOf(1L))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        );

        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("story-reply-delete-request",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }
}
