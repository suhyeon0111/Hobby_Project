package com.hoppy.app.member.service;

import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.login.auth.provider.AuthTokenProvider;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMemberService {

    private final MemberRepository memberRepository;
    private final AuthTokenProvider authTokenProvider;

    public void updateMember(String token, String username, String profileUrl, String intro) {
        String socialPk = authTokenProvider.getSocialId(token);
        Optional<Member> member = memberRepository.findBySocialTypeAndSocialId(SocialType.KAKAO, socialPk);
        member.ifPresent(selectUser-> {
            selectUser.setUsername(username);
            selectUser.setProfileUrl(profileUrl);
            selectUser.setIntro(intro);
            memberRepository.save(selectUser);
        });
    }
}
