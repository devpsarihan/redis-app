package com.redis_app.controller.v1;

import com.redis_app.controller.v1.request.CreateProductRequest;
import com.redis_app.controller.v1.request.UpdateProductRequest;
import com.redis_app.controller.v1.response.GetProductResponse;
import com.redis_app.controller.v1.response.GetProductsResponse;
import com.redis_app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<GetProductResponse> getProductById(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(GetProductResponse.toResponse(productService.getProductById(productId)));
    }

    @GetMapping
    public ResponseEntity<GetProductsResponse> getProducts() {
        return ResponseEntity.ok(GetProductsResponse.toResponse(productService.getProducts()));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<GetProductResponse> updateProduct(@PathVariable Long productId,
        @RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(GetProductResponse.toResponse(productService.updateProduct(productId, request)));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
