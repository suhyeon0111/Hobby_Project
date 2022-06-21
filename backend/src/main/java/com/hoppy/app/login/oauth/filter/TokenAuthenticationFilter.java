package com.hoppy.app.login.oauth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoppy.app.login.oauth.token.AuthToken;
import com.hoppy.app.login.oauth.token.AuthTokenProvider;
import com.hoppy.app.login.utils.HeaderUtil;
import java.io.IOException;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        final String authorizationHeader = request.getHeader("Authorization");
//
//        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            String tokenStr = HeaderUtil.getAccessToken(request);
//            AuthToken token = tokenProvider.convertAuthToken(tokenStr);
//
//            if (token.validate()) {
//                Authentication authentication = tokenProvider.getAuthentication(token);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//            filterChain.doFilter(request, response);
//        } else {
//            System.out.println("There's no Json Web Token");
//        }

        // start
        String authCode = request.getParameter("code");
        log.info("authCode: {}", authCode);

//        RestTemplate rt = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
//        map.add("grant_type", "authorization_code");
//        map.add("client_id", "146414e0f2cf5ef05dee863aae51615a");
//        map.add("code", authCode);
//        map.add("redirect_uri", "http://localhost:8888/login/oauth2/code/kakao");
//        map.add("client_secret", "OrUztJzuetPh4l6KPIpxDkX2bpOeWRmv");
//        HttpEntity<MultiValueMap<String, String>> socialRequest = new HttpEntity<MultiValueMap<String, String>>(map, headers);

//        ResponseEntity<String> resp = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                socialRequest,
//                String.class
//        );
//        log.info("socialRequest: {}", socialRequest);
//        log.info("resp: {}", resp);
        // end

        String tokenStr = HeaderUtil.getAccessToken(request, authCode);
        System.out.println("tokenStr = " + tokenStr);
        AuthToken token = tokenProvider.convertAuthToken(tokenStr);
        System.out.println("token.toString() = " + token.toString());
        if (token.validate()) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
