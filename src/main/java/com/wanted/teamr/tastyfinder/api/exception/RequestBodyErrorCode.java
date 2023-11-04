package com.wanted.teamr.tastyfinder.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum RequestBodyErrorCode implements ErrorCodeType {

   ;

    private final String message;
    private final HttpStatus httpStatus;
    private final String validationCode;

    public static RequestBodyErrorCode getBy(String validationCode) {
        for (RequestBodyErrorCode errorCode : values()) {
            if (validationCode.equals(errorCode.getValidationCode())) {
                return errorCode;
            }
        }
        throw new CustomException(ErrorCode.NOT_FOUND_ERROR_CODE);
    }

}
