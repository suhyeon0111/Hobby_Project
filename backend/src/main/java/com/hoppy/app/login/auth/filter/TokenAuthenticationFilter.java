package com.hoppy.app.login.auth.filter;

import com.hoppy.app.login.auth.token.AuthToken;
import com.hoppy.app.login.auth.provider.AuthTokenProvider;
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
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        System.out.println("TokenAuthenticationFilter.doFilterInternal");

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String tokenStr = HeaderUtil.getAccessToken(request);
            AuthToken token = tokenProvider.convertAuthToken(tokenStr);
            System.out.println("token = " + token);

            if(token.validate()) {  // 토큰 유효성 검사
                Authentication authentication = tokenProvider.getAuthentication(token);
                System.out.println("authentication = " + authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        }
    }
}
