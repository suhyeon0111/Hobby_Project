package com.hoppy.app.login.oauth.service;

import com.hoppy.app.login.oauth.SocialType;
import com.hoppy.app.login.oauth.authentication.AccessTokenSocialTypeToken;
import com.hoppy.app.login.oauth.authentication.OAuth2UserDetails;
import com.hoppy.app.login.oauth.provider.AuthTokenProvider;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class LoadUserService {

    private final RestTemplate restTemplate = new RestTemplate();

    private SocialLoadStrategy socialLoadStrategy;

    private final AuthTokenProvider authTokenProvider;

//    private ApplicationToken app;

    public OAuth2UserDetails getOAuth2UserDetails(AccessTokenSocialTypeToken authentication) {

        SocialType socialType = authentication.getSocialType();

        setSocialLoadStrategy(socialType);

//        String socialPk = socialLoadStrategy.getSocialPk(authentication.getAccessToken());

        Map<String, Object> userInfo = socialLoadStrategy.getUserInfo(authentication.getAccessToken());
        
        return OAuth2UserDetails.builder()
                .socialId(userInfo.get("id").toString())
                .email(userInfo.get("email").toString())
                .username(userInfo.get("nickname").toString())
                .profileUrl(userInfo.get("profile_image").toString())
                .socialType(socialType)
                .build();
    }

    private void setSocialLoadStrategy(SocialType socialType) {
//        this.socialLoadStrategy = switch (socialType) {
//                    case KAKAO -> new KaKaoLoadStrategy();
//                    default -> throw new IllegalArgumentException("지원하지 않는 로그인 형식입니다.");
//                }
        // 임의 수정 : switch 지원  안함
        if (socialType.getSocialName() == "kakao") {
            this.socialLoadStrategy = new KakaoLoadStrategy();
        } else {
            throw new IllegalArgumentException("지원하지 않는 로그인 형식입니다.");
        }
    }
}
