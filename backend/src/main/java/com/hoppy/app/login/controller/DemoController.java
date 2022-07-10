package com.hoppy.app.login.controller;


import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
@RequiredArgsConstructor
public class DemoController {

    /**
     * Test
     * 요청이 들어온 사용자의 socialId 값을 return
     */

    @RequestMapping("/me")
    public String login(Principal principal) {
        System.out.println("principal.getName() = " + principal.getName());
        return principal.getName();
    }

}