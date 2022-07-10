package com.hoppy.app.login.auth.authentication;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }


    @Override
    public String getSocialId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getUsername() {
        Map<String , Object> properties = (Map<String, Object>) attributes.get("properties");
        if (properties.isEmpty()) {
            return null;
        }
        return (String) properties.get("nickname");
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
        return (String) kakao_account.get("email");
    }

    @Override
    public String getProfileImageUrl() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        if (properties == null) {
            return null;
        }

        return (String) properties.get("thumbnail_image");
    }

}
