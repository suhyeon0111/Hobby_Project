package com.hoppy.app.member.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.hoppy.app.login.WithMockCustomUser;
import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class UserControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    MockMvc mvc;

    @BeforeEach
    void setup() {
        /**
         * 초기 가입 멤버의 'deleted' 필드는 'default = false' 설정
         */
        memberRepository.save(
                Member.builder()
                        .username("최대한")
                        .role(Role.USER)
                        .id(8669L)
                        .profileImageUrl("https://www.image.com/test")
                        .socialType(SocialType.KAKAO)
                        .email("test99@naver.com")
                        .password("secret-key")
                        .intro("잘부탁드립니다.")
                        .build()
        );
    }

    @Test
    @WithMockCustomUser(id = "8669")
    void deleteUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.parseLong(authentication.getName());
        Optional<Member> optMember = memberRepository.findById(id);
        assertThat(optMember).isPresent();
        assertThat(optMember.get().isDeleted()).isFalse();
        ResultActions result = mvc.perform(MockMvcRequestBuilders.
                        get("/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType(MediaType.APPLICATION_JSON))
                )
                .andDo(print());
        Member member = memberRepository.findById(id).get();
        /**
         * member의 deleted 필드가 true로 설정됐는지 확인
         */
        assertThat(member.isDeleted()).isTrue();
        result.andExpect(status().isOk())
                .andDo(document("delete-user",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
}