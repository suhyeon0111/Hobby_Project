package com.hoppy.app.login.auth.service;

import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.login.auth.authentication.AccessTokenSocialTypeToken;
import com.hoppy.app.login.auth.authentication.OAuth2UserDetails;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoadUserService {
    private SocialLoadStrategy socialLoadStrategy;

    public OAuth2UserDetails getOAuth2UserDetails(AccessTokenSocialTypeToken authentication) {

        SocialType socialType = authentication.getSocialType();

        setSocialLoadStrategy(socialType);

        Map<String, Object> userInfo = socialLoadStrategy.getUserInfo(authentication.getAccessToken());

        return OAuth2UserDetails.builder()
                .socialId(userInfo.get("id").toString())
                .email(userInfo.get("email").toString())
                .username(userInfo.get("nickname").toString())
                .profileUrl(userInfo.get("profile_image").toString())
                .socialType(socialType)
                .intro(null)
                .build();
        
    }

    
    private void setSocialLoadStrategy(SocialType socialType) {
        if (socialType.getSocialName() == "kakao") {
            this.socialLoadStrategy = new KakaoLoadStrategy();
        } else {
            throw new IllegalArgumentException("지원하지 않는 로그인 형식입니다.");
        }
    }
}
