package com.hoppy.app.login.oauth.handler.service;

import com.hoppy.app.login.oauth.SocialType;
import com.hoppy.app.login.oauth.authentication.OAuth2UserDetails;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.dto.MemberDto;
import com.hoppy.app.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDTOService {

    private final MemberRepository memberRepository;
    /**
     * DTO는 우리 application용 jwt를 포함해서 반환함.
     */
    public MemberDto loadMember(OAuth2UserDetails oAuth2User, String jwt) {
        String socialId = oAuth2User.getSocialId();
        SocialType socialType = oAuth2User.getSocialType();

        if (memberRepository.findBySocialTypeAndSocialId(socialType, socialId).isPresent()) {
            Optional<Member> memberInfo = memberRepository.findBySocialTypeAndSocialId(socialType, socialId);
            return MemberDto.builder()
                    .socialId(memberInfo.get().getSocialId())
                    .email(memberInfo.get().getEmail())
                    .username(memberInfo.get().getUsername())
                    .profileUrl(memberInfo.get().getProfileUrl())
                    .jwt(jwt)
                    .build();
        } else {
            return  MemberDto.builder()
                    .socialId(socialId)
                    .email(oAuth2User.getEmail())
                    .username(oAuth2User.getUsername())
                    .profileUrl(oAuth2User.getProfileUrl())
                    .jwt(jwt)
                    .build();
        }
    }
}
