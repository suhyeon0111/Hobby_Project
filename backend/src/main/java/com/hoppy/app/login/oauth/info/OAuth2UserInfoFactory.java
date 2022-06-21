package com.hoppy.app.login.oauth.info;

import com.hoppy.app.login.oauth.entity.ProviderType;
import com.hoppy.app.login.oauth.info.impl.KaKaoOAuth2UserInfo;
import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case KAKAO: return new KaKaoOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
