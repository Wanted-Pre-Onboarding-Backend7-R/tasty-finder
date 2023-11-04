package com.wanted.teamr.tastyfinder.api.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomErrorResponse {

    private final String code;
    private final String message;

    @Builder
    private CustomErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
