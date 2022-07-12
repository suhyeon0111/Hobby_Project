package com.hoppy.app.login.controller;


import com.hoppy.app.login.auth.provider.AuthTokenProvider;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
@RequiredArgsConstructor
public class DemoController {

    /**
     * Test
     * 요청이 들어온 사용자의 socialId 값을 return
     */

    private final AuthTokenProvider authTokenProvider;

    @RequestMapping("/me")
    public String login(Principal principal) {
        System.out.println("principal.getName() = " + principal.getName());
        return principal.getName();
    }

    @GetMapping("/jwt/{id}")
    public String provideJwt(@PathVariable String id) {
        return authTokenProvider.createUserAuthToken(id).getToken();
    }
}