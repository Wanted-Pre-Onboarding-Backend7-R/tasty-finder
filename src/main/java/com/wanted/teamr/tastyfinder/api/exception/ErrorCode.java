package com.wanted.teamr.tastyfinder.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeType {

    NOT_FOUND_ERROR_CODE("대응하는 에러코드가 존재하지 않습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    ;

    private final String message;
    private final HttpStatus httpStatus;

}
