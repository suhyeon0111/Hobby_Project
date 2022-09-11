package com.hoppy.app.common.tool;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * packageName    : com.hoppy.app.common.service
 * fileName       : LogService
 * author         : Kim
 * date           : 2022-09-08
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-09-08        Kim       최초 생성
 */
public class LogBox {

    @Getter
    @AllArgsConstructor
    public static class InfoBox {
        private String uuid;
        private String requestURI;
    }

    public static void set(String uuid, String requestURI) {
        REQUEST_INFO.set(new InfoBox(uuid, requestURI));
    }

    private static final ThreadLocal<InfoBox> REQUEST_INFO = new ThreadLocal<>();

    public static String getUUID() {
        return REQUEST_INFO.get().getUuid();
    }

    public static String getRequestURI() {
        return REQUEST_INFO.get().getRequestURI();
    }

    public static void remove() {
        /*
         * 톰캣의 경우 자체적으로 ThreadPool을 운영하는데 스레드 재사용으로 인한 클라이언트 상태 공유가 되는 것을 방지하고자 remove를 해주어야 함
         * 물론 이 경우 중요한 정보는 아니지만 그래도...
         *
         * -tae
         * */
        REQUEST_INFO.remove();
    }
}
