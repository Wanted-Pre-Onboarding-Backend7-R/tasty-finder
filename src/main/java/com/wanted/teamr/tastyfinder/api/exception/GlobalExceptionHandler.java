package com.wanted.teamr.tastyfinder.api.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        log.error("CustomException", e);
        ErrorCodeType errorCodeType = e.getErrorCodeType();
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(errorCodeType);
        return makeResponseEntity(errorCodeType.getHttpStatus(), customErrorResponse);
    }

    private ResponseEntity<?> makeResponseEntity(HttpStatus httpStatus, CustomErrorResponse customErrorResponse) {
        return ResponseEntity.status(httpStatus)
                .header(HttpHeaders.CONTENT_ENCODING, Encoding.DEFAULT_CHARSET.name())
                .contentType(MediaType.APPLICATION_JSON)
                .body(customErrorResponse);
    }
}
