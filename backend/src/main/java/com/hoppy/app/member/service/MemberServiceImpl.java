package com.hoppy.app.member.service;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.dto.UpdateMemberDto;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member findById(long id) {
        Optional<Member> optMember = memberRepository.findById(id);
        if(optMember.isEmpty()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return optMember.get();
    }

    @Override
    public Member findByIdWithPostLikes(long id) {
        Optional<Member> optMember = memberRepository.findByIdWithPostLikes(id);
        if(optMember.isEmpty()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return optMember.get();
    }

    @Override
    @Transactional
    public Member updateById(long memberId, UpdateMemberDto memberDto) {
        Optional<Member> optMember = memberRepository.findById(memberId);
        if(optMember.isEmpty()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        Member member = optMember.get();

        member.setUsername(memberDto.getUsername());
        member.setIntro(memberDto.getIntro());
        member.setProfileImageUrl(memberDto.getProfileUrl());

        /*
        * 22.08.13 -tae
        * isPresent check 누락되어 수정하였음
        * jpa dirty checking 으로 업데이트
        * */
        return member;
    }

    @Override
    public Member deleteById(long id) {
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
    public List<Member> infiniteScrollPagingMember(List<Long> memberIdList, long lastId,
            PageRequest pageRequest) {
        return memberRepository.infiniteScrollPagingMember(memberIdList, lastId, pageRequest);
    }

    @Override
    public List<Long> getMeetingLikes(long memberId) {
        Optional<Member> opt = memberRepository.findByIdWithMeetingLikes(memberId);
        if(opt.isEmpty()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return opt.get().getMeetingLikes().stream()
                .map(M -> M.getMeeting().getId())
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkMeetingLiked(long memberId, long meetingId) {
        Optional<Member> opt = memberRepository.findByIdWithMeetingLikes(memberId);
        if(opt.isEmpty()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return opt.get().getMeetingLikes().stream()
                .anyMatch(M -> M.getMeetingId() == meetingId);
    }

}
