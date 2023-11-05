package com.wanted.teamr.tastyfinder.api.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    COMMON_INVALID_PARAMETER("잘못된 파라미터입니다.", BAD_REQUEST),

    AUTH_AUTHENTICATION_FAILED("인증에 실패하셨습니다.", UNAUTHORIZED),
    AUTH_AUTHORIZATION_FAILED("권한이 없습니다.", FORBIDDEN),
    AUTH_EMAIL_EMPTY("이메일이 없습니다.", BAD_REQUEST),
    AUTH_EMAIL_INVALID("잘못된 이메일입니다.", BAD_REQUEST),
    AUTH_JWT_CLAIMS_EMPTY("JWT claims 문자열이 비어 있습니다.", FORBIDDEN),
    AUTH_JWT_EXPIRED("만료된 토큰입니다.", FORBIDDEN),
    AUTH_JWT_INVALID("잘못된 토큰입니다.", FORBIDDEN),
    AUTH_JWT_UNPRIVILEGED("권한이 없는 토큰입니다.", FORBIDDEN),
    AUTH_JWT_UNSUPPORTED("지원되지 않는 토큰입니다.", FORBIDDEN),
    AUTH_MEMBER_NOT_EXISTS("존재하지 않는 사용자입니다.", BAD_REQUEST),
    AUTH_PASSWORD_EMPTY("비밀번호가 없습니다.", BAD_REQUEST),

    MEMBER_NOT_EXISTS("존재하지 않는 사용자입니다.", BAD_REQUEST),
    MEMBER_EMAIL_EMPTY("이메일이 없습니다.", BAD_REQUEST),
    MEMBER_EMAIL_INVALID("잘못된 이메일입니다.", BAD_REQUEST),
    MEMBER_PASSWORD_EMPTY("비밀번호가 없습니다.", BAD_REQUEST),
    MEMBER_LATITUDE_INVALID("잘못된 위도입니다.", BAD_REQUEST),
    MEMBER_LONGITUDE_INVALID("잘못된 경도입니다.", BAD_REQUEST),
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
