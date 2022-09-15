package com.hoppy.app.common.interceptor;

import com.hoppy.app.common.tool.LogUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uuid = UUID.randomUUID().toString();
        String requestURI = request.getMethod() + " " + request.getRequestURL();

        log.info("Request [{}][{}]", uuid, requestURI);
        LogUtility.set(uuid, requestURI);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        log.info("Response {} [{}][{}]", response.getStatus(), LogUtility.getUUID(), handler);
        LogUtility.remove();
    }
}
