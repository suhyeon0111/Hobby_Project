package com.hoppy.app.member.service;

import com.hoppy.app.member.domain.Member;

public interface MemberService {

    public Member findMemberById(Long id);

    public Member deleteMemberById(Long id);
}
