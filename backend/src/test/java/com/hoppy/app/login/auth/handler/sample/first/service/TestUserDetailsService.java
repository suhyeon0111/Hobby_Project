package com.hoppy.app.login.auth.handler.sample.first.service;

import com.hoppy.app.login.auth.handler.sample.first.CustomUser;
import com.hoppy.app.member.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TestUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return new OAuth2UserDetails(1L, SocialType.KAKAO, "8669", "김이박", null, "test@gmail.com", "www.mmm.com");
        return new CustomUser("name", "password", Role.USER);
    }

//    public OAuth2UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return new OAuth2UserDetails(1L, SocialType.KAKAO, "8669", "김이박", null, "test@gmail.com", "www.mmm.com");
//    }

    @PreAuthorize("isAuthenticated()")
    public void print() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser principal = (CustomUser)authentication.getPrincipal();
        principal.printName();
    }
}
