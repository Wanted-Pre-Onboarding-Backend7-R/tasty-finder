package com.wanted.teamr.tastyfinder.api.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    INPUT_VALUE_INVALID("입력 값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),

    AUTH_AUTHENTICATION_FAILED("인증에 실패하셨습니다.", UNAUTHORIZED),
    AUTH_AUTHORIZATION_FAILED("권한이 없습니다.", FORBIDDEN),
    AUTH_EMAIL_EMPTY("이메일이 없습니다.", BAD_REQUEST),
    AUTH_EMAIL_INVALID("잘못된 이메일입니다.", BAD_REQUEST),
    AUTH_JWT_CLAIMS_EMPTY("JWT claims 문자열이 비어 있습니다.", UNAUTHORIZED),
    AUTH_JWT_EXPIRED("만료된 토큰입니다.", UNAUTHORIZED),
    AUTH_JWT_INVALID("잘못된 토큰입니다.", UNAUTHORIZED),
    AUTH_JWT_UNPRIVILEGED("권한이 없는 토큰입니다.", FORBIDDEN),
    AUTH_JWT_UNSUPPORTED("지원되지 않는 토큰입니다.", UNAUTHORIZED),
    AUTH_MEMBER_NOT_EXISTS("존재하지 않는 사용자입니다.", BAD_REQUEST),
    AUTH_PASSWORD_EMPTY("비밀번호가 없습니다.", BAD_REQUEST),


    MEMBER_NOT_EXISTS("존재하지 않는 사용자입니다.", BAD_REQUEST),
    MEMBER_EMAIL_EMPTY("이메일이 없습니다.", BAD_REQUEST),
    MEMBER_EMAIL_INVALID("잘못된 이메일입니다.", BAD_REQUEST),
    MEMBER_PASSWORD_EMPTY("비밀번호가 없습니다.", BAD_REQUEST),
    MEMBER_LATITUDE_INVALID("잘못된 위도입니다.", BAD_REQUEST),
    MEMBER_LONGITUDE_INVALID("잘못된 경도입니다.", BAD_REQUEST),

    MATZIP_NOT_FOUND("맛집 게시물이 존재하지 않습니다.", NOT_FOUND),
    MATZIP_LIST_RETRIEVE_PAGE_NUM_INVALID("맛집 목록 조회 페이지 번호가 유효하지 않습니다.", BAD_REQUEST),
    MATZIP_LIST_RETRIEVE_RANGE_INVALID("맛집 목록 조회 범위가 유효하지 않습니다.", BAD_REQUEST),
    MATZIP_LIST_RETRIEVE_TYPE_INVALID("맛집 목록 조회 타입이 유효하지 않습니다.", BAD_REQUEST),
    MATZIP_LIST_RETRIEVE_CATEGORY_INVALID("맛집 목록 조회 음식점 종류가 유효하지 않습니다.", BAD_REQUEST),
    MATZIP_LIST_RETRIEVE_LATITUDE_INVALID("잘못된 위도입니다.", BAD_REQUEST),
    MATZIP_LIST_RETRIEVE_LONGITUDE_INVALID("잘못된 경도입니다.", BAD_REQUEST),

    REVIEW_RATING_EMPTY("별점 값이 없습니다.", BAD_REQUEST),
    REVIEW_RATING_INVALID("별점 값이 유효하지 않습니다.", BAD_REQUEST),

    COMMON_INVALID_PARAMETER("잘못된 파라미터입니다.", BAD_REQUEST)
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
