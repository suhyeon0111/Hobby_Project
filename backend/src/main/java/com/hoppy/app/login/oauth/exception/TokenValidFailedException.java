package com.hoppy.app.login.oauth.exception;

/***
 * Exception 관련 추가 작업 필요
 */
public class TokenValidFailedException extends RuntimeException {

    public TokenValidFailedException() {
        super("Failed to generate Token");
    }

    private TokenValidFailedException(String message) {
        super(message);
    }
}
