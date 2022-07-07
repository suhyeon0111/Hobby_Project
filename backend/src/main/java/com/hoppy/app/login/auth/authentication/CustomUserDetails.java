package com.hoppy.app.login.auth.authentication;

import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomUserDetails implements OAuth2User, UserDetails, OidcUser {

    private final String socialId;
    private final String password;
    private final SocialType socialType;
    private final Role role;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;


    @Override
    public String getName() {
        return socialId;
    }

    @Override
    public String getUsername() {
        return socialId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    public static CustomUserDetails create(Member member) {
        return new CustomUserDetails(
                member.getSocialId(),
                member.getPassword(),
                member.getSocialType(),
                Role.USER,
                Collections.singletonList(new SimpleGrantedAuthority(Role.USER.toString()))
        );
    }

    public static CustomUserDetails create(Member member, Map<String, Object> attributes) {
        CustomUserDetails customUserDetails = create(member);
        customUserDetails.setAttributes(attributes);
        return customUserDetails;
    }

}
