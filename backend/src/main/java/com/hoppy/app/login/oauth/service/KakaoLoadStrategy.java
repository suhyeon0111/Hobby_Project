package com.hoppy.app.login.oauth.service;

import com.fasterxml.jackson.core.JsonParser;
import com.hoppy.app.login.oauth.SocialType;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

@Slf4j
public class KakaoLoadStrategy extends SocialLoadStrategy{

    protected String sendRequestToSocialSite(HttpEntity request) {
        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(SocialType.KAKAO.getUserInfoUrl(),
                    SocialType.KAKAO.getMethod(),
                    request,
                    RESPONSE_TYPE);
            System.out.println("response.getBody() = " + response.getBody());

            return response.getBody().get("id").toString();
        } catch (Exception e) {
            log.error("KAKAO 사용자 정보를 받아오던 중 에러가 발생했습니다. {}", e.getMessage());
            throw e;
        }
    }
}
