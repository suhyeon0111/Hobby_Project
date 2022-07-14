package com.hoppy.app.mypage.service;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.mypage.dto.MyPageMemberDto;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMemberServiceImpl implements UpdateMemberService {

    private final MemberRepository memberRepository;

    public Member updateMember(Long memberId, MyPageMemberDto memberDto) {
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isPresent()){
            member.get().setUsername(memberDto.getUsername());
            member.get().setProfileImageUrl(memberDto.getProfileUrl());
            member.get().setIntro(memberDto.getIntro());
            memberRepository.save(member.get());
            return member.get();
        } else {
            return null;
        }
    }
}
