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

    public MemberDto loadMember(OAuth2UserDetails oAuth2User, String jwt) {
        String socialId = oAuth2User.getSocialId();
        SocialType socialType = oAuth2User.getSocialType();

        // Member DB에 사용자가 존재한다면, DB에 저장된 사용자 정보를 DTO 로 반환
        // DB에 존재하지 않다면, 카카오 유저 정보를 그대로 DTO 로 반환
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
