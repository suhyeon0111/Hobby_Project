package com.hoppy.app.login.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoppy.app.domain.JwtResponseDto;
import com.hoppy.app.domain.Role;
import com.hoppy.app.login.oauth.authentication.AccessTokenSocialTypeToken;
import com.hoppy.app.login.oauth.authentication.OAuth2UserDetails;
import com.hoppy.app.login.oauth.service.LoadUserService;
import com.hoppy.app.login.oauth.service.SocialLoadStrategy;
import com.hoppy.app.login.oauth.token.AuthToken;
import com.hoppy.app.login.oauth.provider.AuthTokenProvider;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthTokenProvider authTokenProvider;
    private SocialLoadStrategy socialLoadStrategy;
    private final LoadUserService loadUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("로그인 성공!: " + authentication.getPrincipal());
        OAuth2UserDetails oAuth2User = (OAuth2UserDetails) authentication.getPrincipal();

        String email = oAuth2User.getSocialEmail();
        String username = oAuth2User.getUsername();
        String profileUrl = oAuth2User.getProfileUrl();

        System.out.println("oAuth2User.getSocialEmail() = " + oAuth2User.getSocialEmail());
        System.out.println("oAuth2User.getUsername() = " + oAuth2User.getUsername());
        
        AuthToken token = authTokenProvider.createUserAuthToken(oAuth2User.getMemberId());
        System.out.println("jwt = " + token.getToken());
        JwtResponseDto jwtResponseDto = new JwtResponseDto(token.getToken());
        System.out.println("jwtResponseDto = " + jwtResponseDto);

        if(authentication.getAuthorities().stream().anyMatch(s -> s.getAuthority().equals(Role.GUEST.getGrantedAuthority()))) {
            System.out.println("가입되지 않은 유저입니다. 회원가입으로 이동합니다.");
            System.out.println("authentication.getName() = " + authentication.getName());
            System.out.println("authentication.getAuthorities() = " + authentication.getAuthorities());
            System.out.println("authentication.getDetails() = " + authentication.getDetails());
            System.out.println("authentication.getClass() = " + authentication.getClass());
            System.out.println("authentication = " + authentication.getCredentials());
//            response.sendRedirect("/singUp");
            return;
        }

        System.out.println("토큰을 발급합니다.");

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(mapper.writeValueAsString(jwtResponseDto));
        response.getWriter().write(mapper.writeValueAsString(email));
        response.getWriter().write(mapper.writeValueAsString(username));
        response.getWriter().write(mapper.writeValueAsString(profileUrl));
    }
}
