package com.hoppy.app.login.auth.handler.sample;

import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@AutoConfigureRestDocs
@TestInstance(Lifecycle.PER_CLASS)
class OAuth2AuthenticationSuccessHandlerTest {

    private Key key;
    private Date expiry;

    private MemberRepository memberRepository;

    @Test
    void onAuthenticationSuccess() {

        this.key = Keys.hmacShaKeyFor("926D96C90030DD58429D2751AC1BDBBC".getBytes());
        this.expiry = new Date(System.currentTimeMillis() + Long.parseLong("14400000"));

        String jwt = Jwts.builder()
                .setSubject("2249461729")
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();

        System.out.println("jwt = " + jwt);

        Optional<Member> memberInfo = memberRepository.findBySocialTypeAndSocialId(SocialType.KAKAO, "1234567890");
        if(memberInfo.isPresent()) {
            System.out.println("사용자가 존재합니다.");
            System.out.println("memberInfo.get().getUsername() = " + memberInfo.get().getUsername());
            System.out.println("memberInfo.get().getEmail() = " + memberInfo.get().getEmail());
            System.out.println("memberInfo.get().getProfileUrl() = " + memberInfo.get().getProfileUrl());
        } else {
            System.out.println("존재하지 않는 사용자입니다.");
        }
    }

    @Transactional
    @BeforeAll
    void before() {
        memberRepository.save(Member.builder()
                .socialId("1234567890")
                .socialType(SocialType.KAKAO)
                .email("test@naver.com")
                .profileUrl("profileUrl.com")
                .role(Role.USER)
                .username("강해상").build());
    }

    @Transactional
    @AfterAll
    void after() {
        memberRepository.deleteAll();
    }
}