package com.hoppy.app.login.auth.provider;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.login.auth.authentication.AccessTokenSocialTypeToken;
import com.hoppy.app.login.auth.authentication.OAuth2UserDetails;
import com.hoppy.app.login.auth.service.LoadUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccessTokenAuthenticationProvider implements AuthenticationProvider {

    private final LoadUserService loadUserService;
    private final MemberRepository memberRepository;

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        OAuth2UserDetails oAuth2User = loadUserService.getOAuth2UserDetails((AccessTokenSocialTypeToken) authentication);


        Member member = saveOrGet(oAuth2User);
        oAuth2User.setRoles(member.getRole().name());
        oAuth2User.setMemberId(member.getId());


        return AccessTokenSocialTypeToken.builder().principal(oAuth2User).authorities(oAuth2User.getAuthorities()).build();
    }

    private Member saveOrGet(OAuth2UserDetails oAuth2User) {

        return memberRepository
                .findBySocialTypeAndSocialId(
                    oAuth2User.getSocialType(),
                    oAuth2User.getSocialId()
                )
                .orElseGet(() -> memberRepository.save(Member.builder()
                        .socialId(oAuth2User.getSocialId())
                        .socialType(oAuth2User.getSocialType())
                        .email(oAuth2User.getEmail())
                        .profileUrl(oAuth2User.getProfileUrl())
                        .role(Role.USER)
                        .username(oAuth2User.getUsername()).build()));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        /**
         * AccessTokenSocialTypeToken 타입의 authentication 객체이면 해당 Provider 가 처리한다.
         */
        return AccessTokenSocialTypeToken.class.isAssignableFrom(authentication);
    }
}
