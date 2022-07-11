package com.hoppy.app.mypage.service;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMemberServiceImpl implements UpdateMemberService {

    private final MemberRepository memberRepository;

    public Member updateMember(Long memberId, String username, String profileUrl, String intro) {
        Optional<Member> member = memberRepository.findById(memberId);
        member.ifPresent(selectUser-> {
            selectUser.setUsername(username);
            selectUser.setProfileImageUrl(profileUrl);
            selectUser.setIntro(intro);
            memberRepository.save(selectUser);
        });
        return member.get();
    }
}
