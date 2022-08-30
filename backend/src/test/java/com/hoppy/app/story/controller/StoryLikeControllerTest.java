package com.hoppy.app.story.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoppy.app.like.repository.MemberStoryLikeRepository;
import com.hoppy.app.login.WithMockCustomUser;
import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.dto.UploadStoryDto;
import com.hoppy.app.story.repository.StoryRepository;
import com.hoppy.app.story.service.StoryService;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
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
public class StoryLikeControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

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

        for(int i = 1; i <= 20; i++) {
            Member member = null;
            if(i % 2 == 0) {
                member = member1;
            } else if(i % 2 != 0) {
                member = member2;
            }
            storyRepository.save(
                    Story.builder()
                            .member(member)
                            .title(i+"th Story")
                            .content("This is " + i + "th Story")
                            .filePath(i+".jpg")
                            .member(member).build()
            );
        }

//        List<Story> storyList = storyRepository.findAll();
//        for(int i = 0; i < storyList.size(); i++) {
//            if(i % 2 == 0) {
//                storyService.likeStory(member1.getId(), storyList.get(i).getId());
//            }
//            storyService.likeStory(member2.getId(), storyList.get(i).getId());
//        }
    }

    @AfterEach
    void afterEach() {
        memberStoryLikeRepository.deleteAll();
        storyRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("스토리 좋아요 기능 테스트")
    @Test
    @WithMockCustomUser(id = "8669")
    @Transactional
    void storyLikeTest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.parseLong(authentication.getName());
        Member member1 = memberService.findById(id);
        Member member2 = memberService.findById(7601L);
        
        Story story = storyService.findByStoryId(1L);
        storyService.likeStory(id, 1L);
        Story newStory = storyService.findByStoryId(1L);
        System.out.println("story.getTitle() = " + newStory.getTitle());
        System.out.println("story.getContent() = " + newStory.getContent());
        System.out.println("story.getLikes().size() = " + newStory.getLikes().size());
        System.out.println("story.getLikes() = " + newStory.getLikes());
    }
}
