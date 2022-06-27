package com.hoppy.app.health.controller;

import com.hoppy.app.response.service.SuccessCode;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/health")
@RequiredArgsConstructor
public class HealthController {

    private final ResponseService responseService;

    @GetMapping
    public ResponseEntity<ResponseDto> checkHealth() {
        return responseService.successResult(SuccessCode.HEALTH_CHECK_SUCCESS);
    }
}