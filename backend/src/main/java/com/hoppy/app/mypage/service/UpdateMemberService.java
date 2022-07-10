package com.hoppy.app.mypage.service;

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

    public Member updateMember(String socialId, String username, String profileUrl, String intro) {
        Optional<Member> member = memberRepository.findBySocialId(socialId);
        member.ifPresent(selectUser-> {
            selectUser.setUsername(username);
            selectUser.setProfileImageUrl(profileUrl);
            selectUser.setIntro(intro);
            memberRepository.save(selectUser);
        });
        return member.get();
    }
}
