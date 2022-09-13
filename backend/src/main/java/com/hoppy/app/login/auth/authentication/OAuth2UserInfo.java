package com.hoppy.app.login.auth.authentication;

import java.util.Map;

/**
 * 추후 SocialType 추가 대비 추상 클래스 선언
 */

public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getSocialId();

    public abstract String getUsername();

    public abstract String getEmail();

    public abstract String getProfileImageUrl();
}
