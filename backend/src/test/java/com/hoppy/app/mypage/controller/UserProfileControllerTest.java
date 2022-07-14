package com.hoppy.app.mypage.controller;


import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.core.Is.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hoppy.app.EnableMockMvc;
import com.hoppy.app.login.WithMockCustomUser;
import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.dto.MyProfileDto;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.service.ResponseService;
import com.hoppy.app.response.service.SuccessCode;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import javax.print.attribute.standard.Media;
import javax.transaction.Transactional;
import net.minidev.json.parser.JSONParser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 오직 Controller에 대해서만 테스트를 검증할 때는 @WebMvcTest를 사용하면 되지만,
 * @Service, @Repository, JPA 등을 같이 사용해야 하는 로직은 @SpringBootTest & @AutoConfigureMockMvc 사용
 */

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestInstance(Lifecycle.PER_CLASS)
class UserProfileControllerTest {

    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private MockMvc mvc;

//    @MockBean // 모킹하게되면 responseService가 원래 동작대로 동작할 수 없음
//    private ResponseService responseService;

    /**
     * 이미 가입돼있는 가상의 회원
     */
    @BeforeAll
    void makeVirtualMember() {
        memberRepository.save(
                Member.builder()
                        .id(9999L)
                        .socialType(SocialType.KAKAO)
                        .username("강해상")
                        .email("test123@naver.com")
                        .password("secret-key")
                        .profileImageUrl("https://www.xxx.com//myimage")
                        .role(Role.USER)
                        .intro("반갑습니다!")
                        .build()
        );
    }

    @Test
    @WithMockCustomUser(id = "9999", password = "secret-key", role = Role.USER, socialType = SocialType.KAKAO)
    void showMyProfile() throws Exception {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        Optional<Member> optMember = memberRepository.findById(principal.getId());

        assertThat(optMember).isPresent();
        MyProfileDto myProfileDto = MyProfileDto.of(optMember.get());

        mvc.perform(MockMvcRequestBuilders.get("/myprofile")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username", is(myProfileDto.getUsername())))
                .andDo(document("show-myProfile",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()))
                );
    }

    @Test
    void showUserProfile() throws Exception {
        String id = "9999";
        Optional<Member> optMember = memberRepository.findById(Long.parseLong(id));
        assertThat(optMember).isPresent();
        mvc.perform(MockMvcRequestBuilders.get("/userprofile")
                        .param("id", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username", is(optMember.get().getUsername())))
                .andDo(document("show-userProfile",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
}