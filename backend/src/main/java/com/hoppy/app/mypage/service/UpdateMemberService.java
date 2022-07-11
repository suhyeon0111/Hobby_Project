package com.hoppy.app.mypage.service;

import com.hoppy.app.member.domain.Member;

public interface UpdateMemberService {

    public Member updateMember(Long memberId, String username, String profileUrl, String intro);
}
