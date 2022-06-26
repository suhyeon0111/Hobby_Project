package com.hoppy.app.member.service;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Member findMemberById(Long id) {
        Optional<Member> optMember = memberRepository.findById(id);

        if(optMember.isEmpty())
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);

        return optMember.get();
    }
}
