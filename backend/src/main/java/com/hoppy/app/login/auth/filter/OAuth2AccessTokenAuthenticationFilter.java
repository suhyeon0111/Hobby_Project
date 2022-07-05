package com.hoppy.app.login.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.login.auth.authentication.AccessTokenSocialTypeToken;
import com.hoppy.app.login.auth.provider.AccessTokenAuthenticationProvider;
import com.hoppy.app.login.auth.service.LoadUserService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Component
@Slf4j
public class OAuth2AccessTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_OAUTH2_LOGIN_REQUEST_URL_PREFIX = "/login/oauth2/code/";

    private static final String HTTP_METHOD = "GET";

    private static final String ACCESS_TOKEN_HEADER_NAME = "Authorization";

    private final LoadUserService loadUserService;

    private static final AntPathRequestMatcher DEFAULT_OAUTH2_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_OAUTH2_LOGIN_REQUEST_URL_PREFIX + "*", HTTP_METHOD);

    public OAuth2AccessTokenAuthenticationFilter (
            AccessTokenAuthenticationProvider accessTokenAuthenticationProvider,
            AuthenticationSuccessHandler authenticationSuccessHandler,
            AuthenticationFailureHandler authenticationFailureHandler,
            LoadUserService loadUserService) {

        super(DEFAULT_OAUTH2_LOGIN_PATH_REQUEST_MATCHER);


        this.loadUserService = loadUserService;

        // AbstractAuthenticationProcessingFilter 를 커스텀하기 위해 ProviderManager 를 지정
        this.setAuthenticationManager(new ProviderManager(accessTokenAuthenticationProvider));
        this.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        this.setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        /***
         * AbstractAuthenticationProcessingFilter 의 추상 메서드를 구현.
         * Authentication 객체를 반환
         */
        SocialType socialType = extractSocialType(request);

        String authCode = request.getParameter("code");
        log.info("authCode: {}", authCode);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", "146414e0f2cf5ef05dee863aae51615a");
        map.add("code", authCode);
        /**
         * 로컬 테스트용 redirect_uri
         * http://localhost:3000/login/oauth2/code/kakao"
         * hoppy 도메인 redirect_uri
         * https://hoppy.kro.kr/login/oauth2/code/kakao
         */

        map.add("redirect_uri", "http://localhost:8888/login/oauth2/code/kakao");

        HttpEntity<MultiValueMap<String, String>> socialRequest = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> socialResponse = restTemplate.postForEntity("https://kauth.kakao.com/oauth/token", socialRequest , String.class );
        log.info("Social Response: {}", socialResponse);

        Map<String, String> result = new ObjectMapper().readValue(socialResponse.getBody(), Map.class);
        String accessToken = result.get("access_token");

        return this.getAuthenticationManager().authenticate(new AccessTokenSocialTypeToken(accessToken, socialType));
    }


    private SocialType extractSocialType(HttpServletRequest request) {
        return Arrays.stream(SocialType.values())
                .filter(socialType ->
                        socialType.getSocialName()
                                .equals(request.getRequestURI().substring(DEFAULT_OAUTH2_LOGIN_REQUEST_URL_PREFIX.length())))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 URL 주소입니다."));
    }
}
