package com.hoppy.app.login.utils;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaderUtil {

    private final static String AUTHORIZATION_HEADER_PREFIX = "Authorization";
    private final static String AUTHORIZATION_TOKEN_PREFIX = "Bearer ";

    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(AUTHORIZATION_HEADER_PREFIX);

        System.out.println("headerValue = " + headerValue);
        
        if(headerValue == null) {
            System.out.println("headerValue == null");
            return null;
        }

        if(headerValue.startsWith(AUTHORIZATION_TOKEN_PREFIX)) {
            System.out.println("headerValue.substring(AUTHORIZATION_TOKEN_PREFIX.length()) = " + headerValue.substring(AUTHORIZATION_TOKEN_PREFIX.length()));
            return headerValue.substring(AUTHORIZATION_TOKEN_PREFIX.length());
        }
        return null;
    }
}
