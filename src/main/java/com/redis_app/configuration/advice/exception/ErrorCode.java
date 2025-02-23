package com.redis_app.configuration.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    PRODUCT_NOT_FOUND_ERROR("001", "Product is not found.", 404);

    private final String code;
    private final String message;
    private final Integer httpStatusCode;
}
