package com.hoppy.app.common.controller;

import com.hoppy.app.login.auth.provider.AuthTokenProvider;
import com.hoppy.app.response.service.SuccessCode;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/health")
@RequiredArgsConstructor
public class HealthController {

    private final Environment env;
    private final ResponseService responseService;

    private final AuthTokenProvider tokenProvider;

    @GetMapping
    public ResponseEntity<ResponseDto> checkHealth() {
        return responseService.successResult(SuccessCode.HEALTH_CHECK_SUCCESS);
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponseDto> checkProfile() {
        return responseService.successResult(SuccessCode.PROFILE_CHECK_SUCCESS, env.getActiveProfiles());
    }

    @GetMapping("/jwt")
    public ResponseEntity<ResponseDto> getEchoPage() {
        return responseService.successResult(SuccessCode.LOGIN_SUCCESS, tokenProvider.createUserAuthToken("1"));
    }
}