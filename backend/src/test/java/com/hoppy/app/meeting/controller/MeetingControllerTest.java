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
import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.CreateMeetingDto;
import com.hoppy.app.meeting.dto.MeetingJoinDto;
import com.hoppy.app.meeting.dto.MeetingWithdrawalDto;
import com.hoppy.app.meeting.repository.MeetingRepository;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.like.domain.LikeManager;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.like.domain.MemberMeetingLike;
import com.hoppy.app.like.repository.LikeManagerRepository;
import com.hoppy.app.like.repository.MemberMeetingLikeRepository;
import com.hoppy.app.member.repository.MemberMeetingRepository;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_METHOD)
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
    private LikeManagerRepository likeManagerRepository;

    @Autowired
    private MemberMeetingRepository memberMeetingRepository;

    @Autowired
    private MemberMeetingLikeRepository memberMeetingLikeRepository;

    @BeforeEach
    void before() {
        LikeManager likeManager = LikeManager.builder().build();
        likeManager = likeManagerRepository.save(likeManager);

        Member member = Member.builder().id(1L).likeManager(likeManager).build();
        member = memberRepository.save(member);

        for (int i = 0; i < 20; i++) {
            Meeting meeting = meetingRepository.save(Meeting.builder()
                    .ownerId(member.getId())
                    .category(Category.ART)
                    .title("제목(" + (i + 1) + ")")
                    .content("컨텐츠(" + (i + 1) + ")")
                    .memberLimit(15)
                    .build()
            );

            memberMeetingRepository.save(MemberMeeting.builder()
                    .meetingId(meeting.getId())
                    .memberId(member.getId())
                    .build()
            );

            if(i % 3 == 0) {
                memberMeetingLikeRepository.save(MemberMeetingLike.builder()
                        .likeManager(likeManager)
                        .meetingId(meeting.getId())
                        .build()
                );
            }
        }
    }

    @AfterEach
    void after() {
        memberRepository.deleteAll();
        memberMeetingRepository.deleteAll();
        memberMeetingLikeRepository.deleteAll();
        meetingRepository.deleteAll();
    }

    @Test
    @WithMockCustomUser(id = "1", password = "secret-key", role = Role.USER, socialType = SocialType.KAKAO)
    void createMeetingTest() throws Exception {

        CreateMeetingDto createMeetingDto = CreateMeetingDto.builder()
                .filename("testFile.png")
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
    @WithMockCustomUser(id = "1", password = "secret-key", role = Role.USER, socialType = SocialType.KAKAO)
    void getMeetingListByCategoryWithPagingTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/meeting?categoryNumber=2")
                .accept(MediaType.APPLICATION_JSON)
            )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("meeting-pagination",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @WithMockCustomUser(id = "1", password = "secret-key", role = Role.USER, socialType = SocialType.KAKAO)
    void getMeetingDetailTest() throws Exception {

        //given
        List<Meeting> meetingList = meetingRepository.findAll();
        Assertions.assertThat(meetingList.size()).isGreaterThan(0);
        Long testMeetingId = meetingList.get(0).getId();

        //when~then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/meeting/" + testMeetingId)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.participantList[0].owner", is(Boolean.TRUE)))
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("meeting-detail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @WithMockCustomUser(id = "1", password = "secret-key", role = Role.USER, socialType = SocialType.KAKAO)
    void meetingJoinTest() throws Exception {

        //given
        Meeting meeting = Meeting.builder()
                .ownerId(1L)
                .category(Category.ART)
                .title("title")
                .content("content")
                .memberLimit(15)
                .build();
        meeting = meetingRepository.save(meeting);

        MeetingJoinDto meetingJoinDto = MeetingJoinDto.builder()
                .meetingId(meeting.getId())
                .build();

        String content = objectMapper.writeValueAsString(meetingJoinDto);

        //when~then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/meeting/entry")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("meeting-join",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @WithMockCustomUser(id = "1", password = "secret-key", role = Role.USER, socialType = SocialType.KAKAO)
    void meetingWithdrawTest() throws Exception {

        //given
        List<Meeting> meetingList = meetingRepository.findAll();
        Meeting meeting = meetingList.get(0);

        MeetingWithdrawalDto meetingWithdrawalDto = MeetingWithdrawalDto.builder()
                .meetingId(meeting.getId())
                .build();

        String content = objectMapper.writeValueAsString(meetingWithdrawalDto);

        //when~then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/meeting/withdrawal")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("meeting-withdraw",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }
}