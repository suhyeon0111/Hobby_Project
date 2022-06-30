package com.hoppy.app.mypage.controller;

import com.hoppy.app.login.auth.authentication.OAuth2UserDetails;
import java.security.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController {

    @GetMapping("/username1")
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/username2")
    @ResponseBody
    public String currentUser(Authentication authentication) {
        OAuth2UserDetails oAuth2UserDetails = (OAuth2UserDetails) authentication.getPrincipal();
        return oAuth2UserDetails.getUsername();
    }
}
