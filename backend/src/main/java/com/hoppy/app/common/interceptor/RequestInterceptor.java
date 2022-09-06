package com.hoppy.app.common.interceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    @Getter
    @AllArgsConstructor
    public static class InfoBox {
        private String uuid;
        private String requestURI;
    }

    private static final ThreadLocal<InfoBox> REQUEST_INFO = new ThreadLocal<>();
    public static String getUUID() {
        return REQUEST_INFO.get().getUuid();
    }
    public static String getRequestURI() {
        return REQUEST_INFO.get().getRequestURI();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uuid = UUID.randomUUID().toString();
        String requestURI = request.getMethod() + " " + request.getRequestURL();

        REQUEST_INFO.set(new InfoBox(uuid, requestURI));
        log.info("Request [{}][{}]", uuid, requestURI);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        log.info("Response {} [{}][{}]", response.getStatus(), getUUID(), handler);
        /*
         * 톰캣의 경우 자체적으로 ThreadPool을 운영하는데 스레드 재사용으로 인한 클라이언트 상태 공유가 되는 것을 방지하고자 remove를 해주어야 함
         * 물론 이 경우 중요한 정보는 아니지만 그래도...
         *
         * -tae
         * */
        REQUEST_INFO.remove();
    }
}
