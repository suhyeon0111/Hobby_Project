package com.hoppy.app.login.auth.handler.sample.second.mockmvc;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.login.auth.service.SocialLoadStrategy;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.dto.MemberDto;
import com.hoppy.app.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;



@RunWith(SpringRunner.class)
//@AutoConfigureRestDocs
@WebMvcTest
//@WithMockUser(username = "test", roles = "ADMIN")
@WithMockUser
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Autowired
    SocialLoadStrategy socialLoadStrategy;

    private static final String accessToken = "l86bHBne1JTg_wBgauTFW8DCKIkLPwzWHnQjgm5pCj1z7AAAAYG5suEs";

    @Test
    public void 카카오_기가입_유저_실패() throws Exception {
        memberRepository.save(Member.builder()
                .socialId("1234")
                .username("김꽃두레")
                .email("test@naver.com")
                .id(3L)
                .socialType(SocialType.KAKAO)
                .profileUrl("www.xxx.com")
                .build());

        String object = objectMapper.writeValueAsString(MemberDto.builder().socialId("1234")
                .username("김꽃두레").email("test@naver.com").socialId("1234").profileUrl("www.xxx.com").jwt("123").build());

        ResultActions actions = mockMvc.perform(post("/v1/social/signup/kakao")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(object));

        actions
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value("-1008"));
    }
}
