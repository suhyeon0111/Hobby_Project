package com.hoppy.app.member.service;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.dto.UpdateMemberDto;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface MemberService {

    public Member findMemberById(Long id);

    public Member updateMemberById(Long memberId, UpdateMemberDto memberDto);

    public Member deleteMemberById(Long id);

    public List<Member> infiniteScrollPagingMember(List<Long> memberIdList, Long lastId, PageRequest pageRequest);
}
