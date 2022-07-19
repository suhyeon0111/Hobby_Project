package com.hoppy.app.member.service;

import com.hoppy.app.member.domain.Member;

public interface MemberMeetingService {

    public void joinToMeetingById(Long memberId, Long metingId);
}
