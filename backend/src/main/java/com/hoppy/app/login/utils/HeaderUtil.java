package com.hoppy.app.login.utils;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class HeaderUtil {

    private final static String AUTHORIZATION_HEADER_PREFIX = "Authorization";
    private final static String AUTHORIZATION_TOKEN_PREFIX = "Bearer ";

    public static String getAccessToken(HttpServletRequest request, String authCode) {
//        RestTemplate rt = new RestTemplate();
//
//// HttpHeader 오브젝트 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//// HttpBody 오브젝트 생성
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "authorization_code");
//        params.add("client_id", "146414e0f2cf5ef05dee863aae51615a");
//        params.add("redirect_uri", "http://localhost:8888/login/oauth2/code/kakao");
//        params.add("code", authCode);
//        params.add("client_secret", "OrUztJzuetPh4l6KPIpxDkX2bpOeWRmv");
//
//// HttpHeader와 HttpBody를 HttpEntity에 담기 (why? rt.exchange에서 HttpEntity객체를 받게 되어있다.)
//        HttpEntity<MultiValueMap<String, String>> kakaoRequest = new HttpEntity<>(params, headers);
//
//// HTTP 요청 - POST방식 - response 응답 받기
//        ResponseEntity<String> resp = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoRequest,
//                String.class
//        );
//        log.info("socialRequest: {}", kakaoRequest);
//        log.info("resp: {}", resp);

        String headerValue = request.getHeader(AUTHORIZATION_HEADER_PREFIX);
        System.out.println("headerValue = " + headerValue);

        if(headerValue == null) {
            return null;
        }

        if(headerValue.startsWith(AUTHORIZATION_TOKEN_PREFIX)) {
            return headerValue.substring(AUTHORIZATION_TOKEN_PREFIX.length());
        }
        return null;
    }
}
