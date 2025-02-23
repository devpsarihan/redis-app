package com.redis_app.configuration.redis;

import static com.redis_app.configuration.redis.RedisConstant.PRODUCTS_CACHE;
import static com.redis_app.configuration.redis.RedisConstant.PRODUCT_CACHE;
import static com.redis_app.configuration.redis.RedisConstant.TTL;

import io.lettuce.core.RedisURI;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Setter
@Configuration
@EnableCaching
@RequiredArgsConstructor
@Slf4j
public class RedisConfiguration {

    private final RedisProperties redisProperties;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = getRedisStandaloneConfiguration(redisProperties);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean("redisCacheManager")
    public RedisCacheManager redisCacheManager() {
        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(jedisConnectionFactory())
            .withInitialCacheConfigurations(redisCacheConfigurations())
            .build();
    }

    @Bean("redisTemplate")
    public RedisTemplate<?, ?> redisTemplate() {
        final RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    private RedisStandaloneConfiguration getRedisStandaloneConfiguration(RedisProperties redisProperties) {
        RedisURI redisURI = RedisURI.create(redisProperties.getUrl());
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        configuration.setHostName(redisURI.getHost());
        configuration.setPort(redisURI.getPort());
        configuration.setDatabase(redisProperties.getDatabase());
        return configuration;
    }

    private Map<String, RedisCacheConfiguration> redisCacheConfigurations() {
        Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
        RedisCacheConfiguration productRedisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMillis(TTL))
            .disableCachingNullValues();
        RedisCacheConfiguration productsRedisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMillis(TTL))
            .disableCachingNullValues();
        configurationMap.put(PRODUCT_CACHE, productRedisCacheConfiguration);
        configurationMap.put(PRODUCTS_CACHE, productsRedisCacheConfiguration);
        return configurationMap;
    }
}
