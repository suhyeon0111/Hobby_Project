package com.hoppy.app.mypage.service;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.mypage.dto.MyPageMemberDto;

public interface UpdateMemberService {

    public Member updateMember(Long memberId, MyPageMemberDto memberDto);
}
