package com.hoppy.app.login.auth.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * 테스트 코드 확인용 클래스
 */
@Service
public class MessageService {

    @PreAuthorize("isAuthenticated()")
    public String getMessage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Hello " + authentication;
    }
}
