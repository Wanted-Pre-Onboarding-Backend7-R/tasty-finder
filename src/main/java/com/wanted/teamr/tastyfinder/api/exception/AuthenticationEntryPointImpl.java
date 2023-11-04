package com.wanted.teamr.tastyfinder.api.exception;

import static com.wanted.teamr.tastyfinder.api.exception.ErrorCode.AUTH_AUTHENTICATION_FAILED;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.error(authException.getClass().getSimpleName(), authException.getMessage());

        CustomErrorResponse customErrorResponse = CustomErrorResponse.of(AUTH_AUTHENTICATION_FAILED);
        String responseBody = new ObjectMapper().writeValueAsString(customErrorResponse);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding(Encoding.DEFAULT_CHARSET.name());
        response.getWriter().write(responseBody);
    }
}
