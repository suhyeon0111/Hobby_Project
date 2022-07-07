//package com.hoppy.app.member.service;
//import com.hoppy.app.login.auth.SocialType;
//import com.hoppy.app.login.auth.provider.AuthTokenProvider;
//import com.hoppy.app.member.domain.Member;
//import com.hoppy.app.member.dto.LoginMemberDto;
//import com.hoppy.app.member.dto.MyPageMemberDto;
//import com.hoppy.app.member.repository.MemberRepository;
//import java.util.Optional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class MemberDTOService {
//
//    private final MemberRepository memberRepository;
//    private final AuthTokenProvider authTokenProvider;
//    /**
//     * DTO는 우리 application용 jwt를 포함해서 반환함.
//     */
//    public LoginMemberDto loginSuccessResponse(OAuth2UserDetails oAuth2User, String jwt) {
//        String socialId = oAuth2User.getSocialId();
//        SocialType socialType = oAuth2User.getSocialType();
//
//        Optional<Member> memberInfo = memberRepository.findBySocialTypeAndSocialId(socialType, socialId);
//
//        if (memberInfo.isPresent()) {
//            return LoginMemberDto.builder()
//                    .socialId(memberInfo.get().getSocialId())
//                    .email(memberInfo.get().getEmail())
//                    .username(memberInfo.get().getUsername())
//                    .profileUrl(memberInfo.get().getProfileUrl())
//                    .jwt(jwt)
//                    .build();
//        } else {
//            return  LoginMemberDto.builder()
//                    .socialId(socialId)
//                    .email(oAuth2User.getEmail())
//                    .username(oAuth2User.getUsername())
//                    .profileUrl(oAuth2User.getProfileUrl())
//                    .jwt(jwt)
//                    .build();
//        }
//    }
//
//    public MyPageMemberDto myPageResponse(String token) {
//        String socialPk = authTokenProvider.getSocialId(token);
//        Optional<Member> member = memberRepository.findBySocialTypeAndSocialId(SocialType.KAKAO, socialPk);
//        return MyPageMemberDto.builder()
//                .profileUrl(member.get().getProfileUrl())
//                .username(member.get().getUsername())
//                .intro(member.get().getIntro())
//                .build();
//    }
//}
