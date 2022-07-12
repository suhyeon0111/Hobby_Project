package com.hoppy.app.meeting.controller;

import static org.hamcrest.core.Is.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoppy.app.login.WithMockCustomUser;
import com.hoppy.app.login.auth.provider.AuthTokenProvider;
import com.hoppy.app.login.auth.token.AuthToken;
import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.CreateMeetingDto;
import com.hoppy.app.meeting.repository.MeetingRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.member.domain.MemberMeetingLike;
import com.hoppy.app.member.repository.MemberMeetingLikeRepository;
import com.hoppy.app.member.repository.MemberMeetingRepository;
import com.hoppy.app.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@AutoConfigureRestDocs
@SpringBootTest
//@WebMvcTest(value = MeetingController.class,
//        excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.hoppy.app.login.auth.*")})
@AutoConfigureMockMvc
@WithMockUser(username = "test", roles = "USER")
@TestInstance(Lifecycle.PER_CLASS)
class MeetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MemberMeetingRepository memberMeetingRepository;

    @Autowired
    private MemberMeetingLikeRepository memberMeetingLikeRepository;

    @Autowired
    private AuthTokenProvider authTokenProvider;

    String getToken() {
        return authTokenProvider.createUserAuthToken("1").getToken();
    }

    @BeforeAll
    void before() {
        Member member = Member.builder().id(1L).build();
        memberRepository.save(member);

        for (int i = 0; i < 20; i++) {
            Meeting meeting = Meeting.builder()
                    .category(Category.ART)
                    .title(i + "번 제목")
                    .content(i + "번 컨텐츠")
                    .memberLimit(15)
                    .build();

            meeting = meetingRepository.save(meeting);
            memberMeetingRepository.save(MemberMeeting.builder()
                    .meetingId(meeting.getId())
                    .memberId(member.getId())
                    .build()
            );

            if(i % 3 == 0) {
                memberMeetingLikeRepository.save(MemberMeetingLike.builder()
                        .meetingId(meeting.getId())
                        .memberId(member.getId())
                        .build()
                );
            }
        }
    }

    @AfterAll
    void after() {
        memberRepository.deleteAll();
        memberMeetingRepository.deleteAll();
        memberMeetingLikeRepository.deleteAll();
        meetingRepository.deleteAll();
    }

    @Test
    void createMeetingTest() throws Exception {

        CreateMeetingDto createMeetingDto = CreateMeetingDto.builder()
                .url("test")
                .title("testTitle")
                .content("testContent")
                .memberLimit(10)
                .category(1)
                .build();

        String content = objectMapper.writeValueAsString(createMeetingDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/meeting")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(content)
                        .header("Authorization", "Bearer " + getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("message", is("모임 생성 완료")))
                .andDo(document("create-meeting",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    void getMeetingListByCategoryWithPagingTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/meeting?categoryNumber=2")
                .header("Authorization", "Bearer " + getToken())
                .accept(MediaType.APPLICATION_JSON)
            )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("meeting-pagination",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }
}