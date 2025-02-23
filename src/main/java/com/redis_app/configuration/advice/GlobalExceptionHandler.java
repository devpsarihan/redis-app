package com.redis_app.configuration.advice;

import com.redis_app.configuration.advice.exception.RedisApiErrorResponse;
import com.redis_app.configuration.advice.exception.RedisApiException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = RedisApiException.class)
    public ResponseEntity<RedisApiErrorResponse> redisApiExceptionHandler(RedisApiException e) {
        logger.info("Api related exception => {}", e.getMessage());
        RedisApiErrorResponse errorResponse = RedisApiErrorResponse.builder()
            .code(e.getCode())
            .message(e.getMessage())
            .build();
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(e.getHttpStatusCode()));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<RuntimeException> runtimeExceptionHandler(RuntimeException e) {
        logger.error(e.getMessage());
        e.printStackTrace();
        return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

