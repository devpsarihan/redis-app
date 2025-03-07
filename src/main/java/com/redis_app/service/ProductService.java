package com.redis_app.service;

import com.redis_app.controller.v1.request.CreateProductRequest;
import com.redis_app.controller.v1.request.UpdateProductRequest;
import com.redis_app.model.dto.ProductDto;
import com.redis_app.model.entity.Product;
import com.redis_app.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductCacheService productCacheService;
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

    public ProductDto getProductById(final Long productId) {
        return productCacheService.getProductById(productId);
    }

    public List<ProductDto> getProducts() {
        return productCacheService.getProducts();
    }

    public void deleteProduct(final Long productId) {
        productCacheService.deleteProduct(productId);
        productCacheService.evictProductsCache();
    }

    public ProductDto updateProduct(final Long productId, final UpdateProductRequest request) {
        productCacheService.evictProductsCache();
        return productCacheService.updateProduct(productId, request);
    }
}
