package com.hoppy.app.login.auth.service;

import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findBySocialId(username);
        if(member.isEmpty()) {
            throw new UsernameNotFoundException("Can not find username in MemberRepository");
        }
        return CustomUserDetails.create(member.get());
    }
}
