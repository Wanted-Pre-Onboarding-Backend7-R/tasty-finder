package com.wanted.teamr.tastyfinder.api.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;

    private final ErrorCodeType errorCodeType;

    public CustomException(ErrorCodeType errorCodeType) {
        super(errorCodeType.getMessage());
        this.errorCodeType = errorCodeType;
    }

    public CustomException(ErrorCodeType errorCodeType, Throwable cause) {
        super(errorCodeType.getMessage(), cause);
        this.errorCodeType = errorCodeType;

    }

}
