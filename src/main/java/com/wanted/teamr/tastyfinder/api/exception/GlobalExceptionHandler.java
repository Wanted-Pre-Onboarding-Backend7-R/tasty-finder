package com.wanted.teamr.tastyfinder.api.exception;

import static com.wanted.teamr.tastyfinder.api.exception.ErrorCode.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final int FIRST_ERROR_INDEX = 0;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomErrorResponse> handleCustomException(CustomException e) {
        log.error("CustomException", e);
        ErrorCode errorCode = e.getErrorCode();
        return generateResponseEntity(errorCode);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e);
        String firstErrorMessage = getFirstErrorMessage(e);
        ErrorCode errorCode = errorMessageToErrorCode(firstErrorMessage);
        return generateResponseEntity(errorCode);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException", e);
        return generateResponseEntity(BAD_REQUEST, e.getMessage());
    }

    private ErrorCode errorMessageToErrorCode(String firstErrorMessage) {
        try {
            return valueOf(firstErrorMessage);
        } catch (IllegalArgumentException | NullPointerException ex) {
            return COMMON_INVALID_PARAMETER;
        }
    }

    private String getFirstErrorMessage(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return bindingResult
                .getAllErrors()
                .get(FIRST_ERROR_INDEX) //첫 번째 에러만 반환
                .getDefaultMessage();
    }

    private ResponseEntity<CustomErrorResponse> generateResponseEntity(ErrorCode errorCode) {
        return generateResponseEntity(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
    }

    private ResponseEntity<CustomErrorResponse> generateResponseEntity(HttpStatus httpStatue, String message) {
        return generateResponseEntity(httpStatue, null, message);
    }

    private ResponseEntity<CustomErrorResponse> generateResponseEntity(HttpStatus httpStatus, String code, String message) {
        CustomErrorResponse customErrorResponse = CustomErrorResponse.builder()
                .code(code)
                .message(message)
                .build();
        return ResponseEntity
                .status(httpStatus)
                .header(HttpHeaders.CONTENT_ENCODING, Encoding.DEFAULT_CHARSET.name())
                .contentType(MediaType.APPLICATION_JSON)
                .body(customErrorResponse);
    }
}
