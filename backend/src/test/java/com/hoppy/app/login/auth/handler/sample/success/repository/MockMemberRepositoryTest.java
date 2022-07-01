package com.hoppy.app.login.auth.handler.sample.success.repository;

import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MockMemberRepositoryTest {

//    @Mock
    @Autowired
    MemberRepository memberRepository;

    @DisplayName("임의의 사용자 추가 후 로그인 확인")
    @Test
    void loginAndCheckTest() {
        memberRepository.save(
                Member.builder().id(1L).email("test@naver.com").role(Role.USER).username("김테스트").socialType(SocialType.KAKAO)
                        .profileUrl("www.xxx.com")
                        .socialId("86693789").build());
        Optional<Member> member = memberRepository.findBySocialTypeAndSocialId(SocialType.KAKAO, "86693789");
        if(member.isPresent()) {
            System.out.println("존재합니다.");
        } else {
            System.out.println("존재하지 않습니다.");
        }
    }
}
