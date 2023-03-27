package com.imersao.spring.exception;

import feign.FeignException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(FeignException.class)
    public HandlerResponse handleFeignException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        log.error("Error {}", e.getMessage());
        return new HandlerResponse(e.status(), e.getMessage());
    }

    @ExceptionHandler(ClientException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HandlerResponse handleClientException(ClientException e) {
        log.error("Error {}", e.getMessage());
        return new HandlerResponse(500, e.getMessage());
    }
}

@Data
@RequiredArgsConstructor
class HandlerResponse {
    private LocalDateTime datetime = LocalDateTime.now();

    @NonNull
    private Integer status;

    @NonNull
    private String message;
}


