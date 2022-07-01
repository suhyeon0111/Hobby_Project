package com.hoppy.app.login.auth.handler.sample.second.mockuser;

import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.stereotype.Component;

final class WithUserDetailsSecurityContextFactory implements WithSecurityContextFactory<WithUserDetails> {

//    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public WithUserDetailsSecurityContextFactory(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

//    public SecurityContext createSecurityContext(WithUserDetails withUser) {
//        String username = withUser.value();
//        Assert.hasLength(username, "value() must be non-empty String");
//        OAuth2UserDetails principal = (OAuth2UserDetails)userDetailsService.loadUserByUsername(username);
//        Authentication authentication = new AccessTokenSocialTypeToken(principal,
//                principal.getAuthorities());
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authentication);
//        return context;
//    }
    public SecurityContext createSecurityContext(WithUserDetails withUserDetails) {
        String username = withUserDetails.value();
        Assert.hasLength(username, "value() must be non-empty String");
        UserDetails principal = userDetailsService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}
