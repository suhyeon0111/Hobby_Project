package com.hoppy.app.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.core.Is.is;
import com.hoppy.app.login.WithMockCustomUser;
import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class MemberServiceImplTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
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
    void findMeetingByMemberId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.parseLong(authentication.getName());

        Optional<Member> optMember = memberRepository.findById(id);

        Set<MemberMeeting> myMeetings = new HashSet<>();

        MemberMeeting meeting1 = MemberMeeting.builder().memberId(id).meetingId(1L).build();
        myMeetings.add(meeting1);

        optMember.get().setMyMeetings(myMeetings);

        assertThat(optMember.get().getMyMeetings().size()).isEqualTo(1);
    }
}