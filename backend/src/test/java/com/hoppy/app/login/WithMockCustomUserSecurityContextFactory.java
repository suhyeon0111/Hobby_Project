package com.hoppy.app.login;

import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.member.Role;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        System.out.println("WithMockCustomUserSecurityContextFactory.createSecurityContext");
        
        CustomUserDetails userDetails = new CustomUserDetails(
                Long.parseLong(customUser.id()), customUser.password(), SocialType.KAKAO, Role.USER, Collections.singletonList(new SimpleGrantedAuthority(Role.USER.toString()))
        );


        System.out.println("userDetails.getName() = " + userDetails.getName());
        System.out.println("userDetails.getPassword() = " + userDetails.getPassword());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        context.setAuthentication(authentication);
        return context;
    }
}
