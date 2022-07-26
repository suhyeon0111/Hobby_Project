package com.hoppy.app.health.controller;

import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.service.ResponseService;
import com.hoppy.app.response.service.SuccessCode;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 태경 2022-07-26
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final Environment env;
    private final ResponseService responseService;

    @GetMapping
    public ResponseEntity<ResponseDto> checkHealth(@RequestParam("token") String token) {
        log.info(token);
        return responseService.successResult(SuccessCode.HEALTH_CHECK_SUCCESS, token);
    }
}