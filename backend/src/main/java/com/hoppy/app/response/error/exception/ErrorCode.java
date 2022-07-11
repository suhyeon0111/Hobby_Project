package com.hoppy.app.response.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "입력 값 오류입니다"),
    METHOD_NOT_ALLOWED(405, "C002", "허용되지 않는 api 입니다"),
    ENTITY_NOT_FOUND(400, "C003", "찾을 수 없는 아이템입니다"),
    INTERNAL_SERVER_ERROR(500, "C004", "서버 에러 : 서버 팀에 문의"),
    INVALID_TYPE_VALUE(400, "C005", "타입 오류입니다"),
    HANDLE_ACCESS_DENIED(403, "C006", "접근이 제한되었습니다"),

    // Member

    // JWT
    EXPIRED_ACCESS_TOKEN(403, "J001", "access 토큰이 만료되었습니다"),
    UNSUPPORTED_JWT(403, "J002", "지원하지 않는 JWT 토큰입니다"),
    SIGNATURE_INVALID_JWT(403, "JOO3", "토큰 Signature 오류"),
    JWT_NOT_FOUND(403, "J004", "Jwt 토큰을 찾을 수 없습니다"),
    IlLEGAL_JWT(403, "J005", "적절하지 못한 Jwt 토큰 형식입니다."),

    // s3
    S3_ACCESS_FAIL(500, "S001", "S3 버킷 접근 에러"),

    // Meeting
    TITLE_DUPLICATE(403, "M001", "이미 존재하는 모임 제목입니다"),
    CATEGORY_ERROR(403, "M002", "잘못된 카테고리 번호입니다"),

    // Member
    MEMBER_NOT_FOUND(403, "MM001", "존재하지 않는 멤버입니다"),

    ;
    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return this.code;
    }

    public int getStatus() {
        return this.status;
    }

    private static final Map<String, String> CODE_MAP = Collections.unmodifiableMap(Stream.of(values()).collect(
            Collectors.toMap(ErrorCode::getCode, ErrorCode::name)));

    public static ErrorCode of(final String code) {
        return ErrorCode.valueOf(CODE_MAP.get(code));
    }

}