package com.hoppy.app.member.service;
import com.hoppy.app.login.auth.provider.AuthTokenProvider;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.dto.LoginMemberDto;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.mypage.service.UpdateMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDTOService {

    /**
     * 로그인 성공 시 DB에 존재하는 사용자 정보를 반환
     */
    public LoginMemberDto loginSuccessResponse(Member member) {
        return LoginMemberDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .email(member.getEmail())
                .profileUrl(member.getProfileImageUrl())
                .intro(member.getIntro())
                .build();
    }

}
