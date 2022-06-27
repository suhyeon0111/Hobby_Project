package com.hoppy.app.meeting.controller;

import static org.hamcrest.core.Is.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoppy.app.login.auth.config.SecurityConfig;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.CreateMeetingDto;
import com.hoppy.app.meeting.service.MeetingInquiryService;
import com.hoppy.app.meeting.service.MeetingManageService;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.service.ResponseService;
import com.hoppy.app.response.service.SuccessCode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureRestDocs
@WebMvcTest(value = MeetingController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.hoppy.app.login.auth.*")})
@WithMockUser(username = "test", roles = "USER")
class MeetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MeetingInquiryService meetingInquiryService;

    @MockBean
    private MeetingManageService meetingManageService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private ResponseService responseService;

    @Test
    void createMeetingTest() throws Exception {

        CreateMeetingDto createMeetingDto = CreateMeetingDto.builder()
                .filename("test")
                .title("testTitle")
                .content("testContent")
                .memberLimit(10)
                .memberId(1L)
                .category(1)
                .build();

        Meeting meeting = Meeting.dtoToMeeting(createMeetingDto);
        Member member = Member.builder().id(1L).build();
        SuccessCode responseCode = SuccessCode.CREATE_MEETING_SUCCESS;
        ResponseEntity<ResponseDto> responseEntity = new ResponseEntity<>(
                ResponseDto.commonResponse(responseCode.getStatus(), responseCode.getMessage()),
                HttpStatus.valueOf(responseCode.getStatus()));

        Mockito.when(meetingManageService.createMeeting(Mockito.any(CreateMeetingDto.class)))
                .thenReturn(meeting);
        Mockito.when(memberService.findMemberById(Mockito.any(Long.class)))
                .thenReturn(member);
        Mockito.when(responseService.successResult(Mockito.any(SuccessCode.class)))
                .thenReturn(responseEntity);

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
}