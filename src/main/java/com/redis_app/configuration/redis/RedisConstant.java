package com.redis_app.configuration.redis;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisConstant {

    public static final String PRODUCT_CACHE = "product";
    public static final String PRODUCTS_CACHE = "products";
    public static final Long TTL = 60000L;
}
