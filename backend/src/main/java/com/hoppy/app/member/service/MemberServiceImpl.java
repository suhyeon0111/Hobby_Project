package com.hoppy.app.member.service;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.dto.UpdateMemberDto;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member findMemberById(Long id) {
        Optional<Member> optMember = memberRepository.findById(id);

        if(optMember.isEmpty())
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);

        return optMember.get();
    }

    @Override
    public Member updateMemberById(Long memberId, UpdateMemberDto memberDto) {
        Optional<Member> member = memberRepository.findById(memberId);
        member.ifPresent(selectMember -> {
            selectMember.setUsername(memberDto.getUsername());
            selectMember.setIntro(memberDto.getIntro());
            selectMember.setProfileImageUrl(memberDto.getProfileUrl());
            memberRepository.save(selectMember);
        });
        return member.get();
    }

    @Override
    public Member deleteMemberById(Long id) {
        Optional<Member> optMember = memberRepository.findById(id);
        if(optMember.isPresent()) {
            if(optMember.get().isDeleted()) {
                throw new BusinessException(ErrorCode.DELETED_MEMBER);
            } else {
                optMember.get().setDeleted(true);
                memberRepository.save(optMember.get());
            }
        } else {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return optMember.get();
    }

    @Override
    public List<Member> infiniteScrollPagingMember(List<Long> memberIdList, Long lastId,
            PageRequest pageRequest) {
        return memberRepository.infiniteScrollPagingMember(memberIdList, lastId, pageRequest);
    }

}
