package com.hoppy.app.login.auth.service;

import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.login.auth.authentication.KakaoOAuth2UserInfo;
import com.hoppy.app.login.auth.authentication.OAuth2UserInfo;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        SocialType socialType = SocialType.KAKAO;

        OAuth2UserInfo userInfo = new KakaoOAuth2UserInfo(user.getAttributes());
        System.out.println("userInfo.getSocialId() = " + userInfo.getSocialId());
        Optional<Member> savedMember = memberRepository.findBySocialId(userInfo.getSocialId());

        /**
         * (Note: 새로운 Member 선언 말고 다른 방법 고민할 것)
         * 사용자가 존재할 경우, DB에 존재하는 사용자 정보와 비교해서 달라진 것을 자동으로 Update
         * 사용자가 존재하지 않을 경우, 카카오 정보를 토대로 DB에 저장.
         */

        if(savedMember.isPresent()) {
            updateMember(savedMember.get(), userInfo);
            return CustomUserDetails.create(savedMember.get(), user.getAttributes());
        } else {
            Member member = createMember(userInfo, socialType);
            return CustomUserDetails.create(member, user.getAttributes());
        }
    }

    private Member createMember(OAuth2UserInfo userInfo, SocialType socialType) {

        Member member = Member.builder()
                .socialType(socialType)
                .id(Long.parseLong(userInfo.getSocialId()))
                .email(userInfo.getEmail())
                .profileImageUrl(userInfo.getProfileImageUrl())
                .username(userInfo.getUsername())
                .role(Role.USER).build();

        return memberRepository.saveAndFlush(member);
    }

    private Member updateMember(Member member, OAuth2UserInfo userInfo) {
        /**
         * update(프로필 수정)를 위와 같이 자동으로 처리할지, update 전용 api를 생성해서 요청시에만 수정할지 고민할 필요 있음.
         */
        if (userInfo.getUsername() != null && !member.getUsername().equals(userInfo.getUsername())) {
            member.setUsername(userInfo.getUsername());
        }
        if(userInfo.getProfileImageUrl() != null && !member.getProfileImageUrl().equals(userInfo.getProfileImageUrl())) {
            member.setProfileImageUrl(userInfo.getProfileImageUrl());
        }
        return member;
    }

    @PreAuthorize("isAuthenticated()")
    public String saveMember(Member member) {
        return memberRepository.save(member).getSocialId();
    }

    @PreAuthorize("isAuthenticated()")
    public void print() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        principal.printName();
    }
}
