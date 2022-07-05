package com.hoppy.app.login.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoppy.app.login.auth.service.MessageService;
import com.hoppy.app.member.service.MemberDTOService;
import com.hoppy.app.member.Role;
import com.hoppy.app.login.auth.authentication.OAuth2UserDetails;
import com.hoppy.app.login.auth.token.AuthToken;
import com.hoppy.app.login.auth.provider.AuthTokenProvider;
import com.hoppy.app.member.dto.LoginMemberDto;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthTokenProvider authTokenProvider;

    private final MemberDTOService memberDTOService;

    /**
     * 테스트 코드 확인용 인스턴스
     */
    private final MessageService messageService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("로그인 성공!: " + authentication.getPrincipal());
        OAuth2UserDetails oAuth2User = (OAuth2UserDetails) authentication.getPrincipal();

        AuthToken token = authTokenProvider.createUserAuthToken(oAuth2User.getSocialId());

        if(authentication.getAuthorities().stream().anyMatch(s -> s.getAuthority().equals(Role.GUEST.getGrantedAuthority()))) {
            System.out.println("가입되지 않은 유저입니다. 회원가입으로 이동합니다.");
//            response.sendRedirect("/singUp");
            return;
        }
        /**
         * 각 사용자별 SocialType 과 고유 식별 번호인 socialID 로 db 조회
         * DB 에서 조회한 후 가입한 회원일 경우, DB에 존재하는 멤버 정보를 응답,
         * DB에 존재하지 않은 회원일 경우, 카카오를 통해 얻은 유저 정보 그대로 응답.
         * 이후 사용자가 자신의 정보를 바꾸면, socialPK 값으로 구분
         */

        LoginMemberDto memberDto = memberDTOService.loginSuccessResponse(oAuth2User, token.getToken());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(mapper.writeValueAsString(memberDto));
    }

    /**
     * 테스트용 코드
     */
    @PreAuthorize("isAuthenticated()")
    public void printInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2UserDetails principal = (OAuth2UserDetails) authentication.getPrincipal();
        System.out.println("principal.getUsername() = " + principal.getUsername());
    }
}
