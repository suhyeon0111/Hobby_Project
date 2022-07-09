package com.hoppy.app.login.auth.handler;

import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.login.auth.service.MessageService;
import com.hoppy.app.login.auth.provider.AuthTokenProvider;
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
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthTokenProvider authTokenProvider;

    /**
     * 테스트 코드 확인용 인스턴스
     */
    private final MessageService messageService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("로그인 성공!: " + authentication.getPrincipal());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        System.out.println("(" + userDetails.getName() + ")" + "님이 로그인 했습니다.");

        String jwt = authTokenProvider.createUserAuthToken(userDetails.getId().toString()).getToken();
        System.out.println("jwt = " + jwt);

        String url = makeRedirectUrl(jwt);
        if(response.isCommitted()) {
            logger.debug("응답이 이미 커밋된 상태입니다. " + url + "로 리다이렉트하도록 바꿀 수 없습니다.");
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, url);
    }

    /**
     * 로그인 성공 시 아래 url로 jwt를 추가하여 redirect
     * redirect Url 은 front-end 측으로 맞춰야함. 현재는 내 로컬 (localhost:8888)로 설정
     */
    private String makeRedirectUrl(String token) {
        return UriComponentsBuilder.fromUriString("http://localhost:8888/oauth2/redirect/" + token).build().toString();
    }

}
