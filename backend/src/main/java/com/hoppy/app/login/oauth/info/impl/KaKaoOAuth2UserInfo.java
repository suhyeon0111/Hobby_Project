package com.hoppy.app.login.oauth.info.impl;

import com.hoppy.app.login.oauth.info.OAuth2UserInfo;
import java.util.Map;

public class KaKaoOAuth2UserInfo extends OAuth2UserInfo {

    public KaKaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        System.out.println("attributes.get(\"id\").toString() = " + attributes.get("id").toString());
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        if (properties == null) {
            return null;
        }

        return (String) properties.get("nickname");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("account_email");
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        if(properties == null) {
            return null;
        }

        return (String) properties.get("thumbnail_image");
    }
}
