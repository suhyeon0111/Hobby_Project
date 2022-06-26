package com.hoppy.app.meeting.controller;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoppy.app.EnableMockMvc;
import com.hoppy.app.meeting.dto.CreateMeetingDto;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberMeetingRepository;
import com.hoppy.app.member.repository.MemberRepository;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@ExtendWith(SpringExtension.class)
@EnableMockMvc
@AutoConfigureRestDocs
@TestInstance(Lifecycle.PER_CLASS)
class MeetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMeetingRepository memberMeetingRepository;

    @Transactional
    @BeforeAll
    void before() {
        Member member = Member.builder().build();
        memberRepository.save(member);
    }

    @Transactional
    @AfterAll
    void after() {
        memberMeetingRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Transactional
    @Test
    void createMeetingTest() throws Exception{

        CreateMeetingDto createMeetingDto = CreateMeetingDto.builder()
                .filename("test")
                .title("testTitle")
                .content("testContent")
                .memberLimit(10)
                .memberId(1L)
                .category(1)
                .build();

        String content = objectMapper.writeValueAsString(createMeetingDto);
        System.out.println(content);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/meeting")
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
}