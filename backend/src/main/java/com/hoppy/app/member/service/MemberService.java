package com.hoppy.app.member.service;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.dto.UpdateMemberDto;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface MemberService {

    public Member findById(Long id);

    public Member updateById(Long memberId, UpdateMemberDto memberDto);

    public Member deleteById(Long id);

    public List<Member> infiniteScrollPagingMember(List<Long> memberIdList, Long lastId, PageRequest pageRequest);
}
