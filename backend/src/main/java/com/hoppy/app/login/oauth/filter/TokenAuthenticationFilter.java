package com.hoppy.app.login.oauth.filter;

import com.hoppy.app.login.oauth.token.AuthToken;
import com.hoppy.app.login.oauth.provider.AuthTokenProvider;
import com.hoppy.app.login.utils.HeaderUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
//@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        System.out.println("##########################");
        System.out.println("authorizationHeader = " + authorizationHeader);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String tokenStr = HeaderUtil.getAccessToken(request);
            AuthToken token = tokenProvider.convertAuthToken(tokenStr);
            log.info("tokenStr: " + tokenStr);
            log.info("token:" + token);

            if(token.validate()) {  // 토큰 유효성 검사
                System.out.println("유효한 토큰입니다.");
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("유효하지 않은 토큰입니다.");
            }

            filterChain.doFilter(request, response);
        } else {
            System.out.println("There's No Jwt in Request Header.");
        }
    }
}
