package com.wanted.teamr.tastyfinder.api.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        log.error("CustomException", e);
        ErrorCodeType errorCodeType = e.getErrorCodeType();
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(errorCodeType);
        return makeResponseEntity(errorCodeType.getHttpStatus(), customErrorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<?> handle(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);

        FieldError firstFieldError = e.getBindingResult().getFieldErrors().get(0);
        String validationCode = Objects.requireNonNull(firstFieldError.getCodes())[1];
        RequestBodyErrorCode requestBodyErrorCode = RequestBodyErrorCode.getBy(validationCode);
        log.error("validationCode: {}", validationCode);
        log.error("requestBodyErrorCode: {name:{}, message:{}}", requestBodyErrorCode.name(), requestBodyErrorCode.getMessage());

        CustomErrorResponse body = new CustomErrorResponse(requestBodyErrorCode);
        return makeResponseEntity(requestBodyErrorCode.getHttpStatus(), body);
    }

    private static ResponseEntity<?> makeResponseEntity(HttpStatus status, CustomErrorResponse body) {
        return ResponseEntity.status(status)
                .header(HttpHeaders.CONTENT_ENCODING, Encoding.DEFAULT_CHARSET.name())
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

}
