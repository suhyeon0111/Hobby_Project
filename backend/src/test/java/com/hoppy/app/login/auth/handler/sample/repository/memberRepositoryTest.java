package com.hoppy.app.login.auth.handler.sample.repository;

import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

@SpringBootTest
@Transactional
public class memberRepositoryTest {

    @Autowired
    public MemberRepository memberRepository;

    @Test
    @WithAnonymousUser
    @DisplayName("권한 없이 실행 가능")
    void find() {

        memberRepository.save(Member.builder().email("test@gmail.com").username("ChoiMax").profileUrl("www.xxx.com").socialId("86693789").socialType(
                SocialType.KAKAO).role(Role.USER).build());

        Optional<Member> tmp = memberRepository.findBySocialTypeAndSocialId(SocialType.KAKAO, "1234567890");

        List<Member> members = memberRepository.findAll();

        /**
         * findBySocialTypeAndSocialId() 로 일부 멤버 찾기
         */
        if(tmp.isPresent()) {
            System.out.println("tmp.get().getUsername() = " + tmp.get().getUsername());
        } else {
            System.out.println("존재하지 않습니다.");
        }

        /**
         * findAll() 로 모든 멤버 조회
         */
        if(members.isEmpty()) {
            System.out.println("현재 멤버가 없습니다.");
        } else {
            for(Member member : members) {
                System.out.println("name = " + member.getUsername());
            }
        }
    }

    /**
     * @WithUserDetails가 @Before 전에 실행되므로, 임의의 멤버 데이터를 넣고 싶을 경우 '@BeforeTransaction' 사용
     */
    @BeforeTransaction
    void before() {
        Member member = memberRepository.save(Member.builder()
                .socialId("1234567890")
                .socialType(SocialType.KAKAO)
                .email("test@naver.com")
                .profileUrl("profileUrl.com")
                .role(Role.USER)
                .username("강해상").build());
    }

    @AfterTransaction
    void after() {
        memberRepository.deleteAll();
    }
}
