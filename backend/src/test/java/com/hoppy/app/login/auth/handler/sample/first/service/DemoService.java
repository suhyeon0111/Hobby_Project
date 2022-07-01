package com.hoppy.app.login.auth.handler.sample.first.service;

import com.hoppy.app.login.auth.handler.sample.first.CustomUser;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private final MemberRepository memberRepository;

    public DemoService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PreAuthorize("isAuthenticated()")
    public String save(Member member) {
        return memberRepository.save(member).getUsername();
    }

    @PreAuthorize("isAuthenticated()")
    public void print() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser principal = (CustomUser)authentication.getPrincipal();
        principal.printName();
    }
}

