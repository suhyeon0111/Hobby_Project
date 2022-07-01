package com.hoppy.app.login.auth.handler.sample.second.mockuser;

import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.login.auth.authentication.AccessTokenSocialTypeToken;
import com.hoppy.app.login.auth.authentication.OAuth2UserDetails;
import com.hoppy.app.member.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        OAuth2UserDetails principal = new OAuth2UserDetails(2L, SocialType.KAKAO, "8669", customUser.username(),
                null, "test@gmail.com", "www.xxx.com");
        Authentication auth = new AccessTokenSocialTypeToken(principal, principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }

}
