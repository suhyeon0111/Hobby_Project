package com.hoppy.app.story.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoppy.app.login.WithMockCustomUser;
import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.dto.UploadStoryDto;
import com.hoppy.app.story.repository.StoryRepository;
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

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class StoryControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    StoryRepository storyRepository;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        /*Member member = Member.builder()
                .username("최대한")
                .role(Role.USER)
                .id(8669L)
                .profileImageUrl("https://www.image.com/test")
                .socialType(SocialType.KAKAO)
                .email("test99@naver.com")
                .password("secret-key")
                .intro("잘부탁드립니다.")
                .build();

        memberRepository.save(member);

        for(int i = 1; i <= 5; i++) {
            storyRepository.save(
                    Story.builder()
                            .member(member)
                            .title(i+"th Story")
                            .content("This is " + i + "th Story")
                            .filePath(i+".jpg")
                            .member(member).build()
            );
        }*/

        Member member1 = Member.builder().id(8669L).username("최대한").build();
        Member member2 = Member.builder().id(7601L).username("김태경").build();

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
    }

    @AfterEach
    void afterEach() {
        storyRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("스토리 업로드 테스트")
    @Test
    @WithMockCustomUser(id = "8669")
    void uploadStory() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.parseLong(authentication.getName());
        Optional<Member> optMember = memberRepository.findById(id);
        assertThat(optMember.isPresent()).isTrue();
        UploadStoryDto dto = UploadStoryDto.builder().title("Story Upload Test").content("This is Test code").filename("example.jpg").build();
        String content = objectMapper.writeValueAsString(dto);
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post("/story")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        ).andDo(print());
        result.andExpect(status().isOk())
                .andDo(document("upload-story",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @DisplayName("스토리 수정 테스트")
    @Test
    @WithMockCustomUser(id = "8669")
    void updateStory() throws Exception {
        List<Story> stories = storyRepository.findAll();
        Long storyId = stories.get(0).getId();
        Optional<Story> optStory = storyRepository.findById(storyId);
        assertThat(optStory.isPresent()).isTrue();
        UploadStoryDto dto = UploadStoryDto.builder().title("Update Story Test").content("This is updated story").filename("test_success.wav").build();
        String content = objectMapper.writeValueAsString(dto);
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .put("/story")
                .param("id", String.valueOf(storyId))
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        ).andDo(print());
        result.andExpect(status().isOk())
                .andDo(document("update-story",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @DisplayName("스토리 삭제 테스트")
    @Test
    void deleteStory() throws Exception {
        List<Story> stories = storyRepository.findAll();
        Long storyId = stories.get(0).getId();
        Optional<Story> optStory = storyRepository.findById(storyId);
        assertThat(optStory.isPresent()).isTrue();

        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .delete("/story")
                .param("id", String.valueOf(storyId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        ).andDo(print());

        Optional<Story> deletedStory = storyRepository.findById(storyId);
        assertThat(deletedStory.isEmpty()).isTrue();
        result.andExpect(status().isOk())
                .andDo(document("delete-story",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @DisplayName("스토리 조회 페이징 테스트")
    @Test
    void showStoryList() throws Exception {
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .get("/story")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        ).andDo(print());
        result.andExpect(status().isOk())
                .andDo(document("story-pagination",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }
}