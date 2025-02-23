package com.redis_app.service;

import static com.redis_app.configuration.redis.RedisConstant.PRODUCTS_CACHE;
import static com.redis_app.configuration.redis.RedisConstant.PRODUCT_CACHE;

import com.redis_app.configuration.advice.exception.ErrorCode;
import com.redis_app.configuration.advice.exception.RedisApiException;
import com.redis_app.controller.v1.request.CreateProductRequest;
import com.redis_app.controller.v1.request.UpdateProductRequest;
import com.redis_app.model.dto.ProductDto;
import com.redis_app.model.entity.Product;
import com.redis_app.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCacheService {

    private final ProductRepository productRepository;

    public Long createProduct(final CreateProductRequest request) {
        return productRepository.save(
            Product.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .count(request.getCount())
                .build()).getId();
    }

    @Cacheable(value = PRODUCT_CACHE, key = "#productId", unless = "#result == null")
    public ProductDto getProductById(final Long productId) {
        return productRepository.findById(productId).map(Product::toProductDto)
            .orElseThrow(() -> new RedisApiException(ErrorCode.PRODUCT_NOT_FOUND_ERROR));
    }

    @Cacheable(value = PRODUCTS_CACHE, key = "#root.methodName", unless = "#result == null")
    public List<ProductDto> getProducts() {
        List<ProductDto> products = productRepository.findAll().stream().map(Product::toProductDto).toList();
        return products;
    }

    @CacheEvict(value = PRODUCT_CACHE, key = "#productId")
    public void deleteProduct(final Long productId) {
        productRepository.deleteById(productId);
    }

    @CachePut(value = PRODUCT_CACHE, key = "#productId", unless = "#result == null")
    public ProductDto updateProduct(final Long productId, final UpdateProductRequest request) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RedisApiException(ErrorCode.PRODUCT_NOT_FOUND_ERROR));
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setCount(request.getCount());
        product.setPrice(request.getPrice());
        productRepository.save(product);
        return product.toProductDto();
    }

    @CacheEvict(value = PRODUCTS_CACHE, allEntries = true)
    public void evictProductsCache() {
        log.info("Evict products cache");
    }
}
