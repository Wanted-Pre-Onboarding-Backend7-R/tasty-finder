package com.wanted.teamr.tastyfinder.api.exception;

import static org.springframework.http.HttpStatus.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeType {

    AUTH_JWT_CLAIMS_EMPTY("JWT claims string is empty.", FORBIDDEN),
    AUTH_JWT_EXPIRED("Expired JWT Token", FORBIDDEN),
    AUTH_JWT_INVALID("Invalid JWT Token", FORBIDDEN),
    AUTH_JWT_UNPRIVILEGED("Unprivileged JWT Token", FORBIDDEN),
    AUTH_JWT_UNSUPPORTED("Unsupported JWT Token", FORBIDDEN),

    MEMBER_NOT_EXISTS("존재하지 않는 사용자입니다.", FORBIDDEN),
    NOT_FOUND_ERROR_CODE("대응하는 에러코드가 존재하지 않습니다.", HttpStatus.INTERNAL_SERVER_ERROR)
    ;

    private final String message;
    private final HttpStatus httpStatus;

}
