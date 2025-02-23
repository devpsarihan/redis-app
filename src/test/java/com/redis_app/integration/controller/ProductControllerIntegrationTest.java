package com.redis_app.integration.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.redis_app.controller.v1.request.CreateProductRequest;
import com.redis_app.controller.v1.request.UpdateProductRequest;
import com.redis_app.controller.v1.response.GetProductsResponse;
import com.redis_app.integration.TestContainersConfiguration;
import com.redis_app.model.dto.ProductDto;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ProductControllerIntegrationTest extends TestContainersConfiguration {

    private static CreateProductRequest request;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp() {
        request = CreateProductRequest.builder()
            .title("Sample Product")
            .description("Description")
            .price(BigDecimal.valueOf(100))
            .count(10)
            .build();
    }

    @Test
    void testCreateProduct_WhenProduct_ShouldReturnProductId() {
        ResponseEntity<Long> response = testRestTemplate.exchange("/v1/products", HttpMethod.POST,
            new HttpEntity<>(request), Long.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetProductById_WhenGivenProductId_ShouldReturnProduct() {
        Long productId = testRestTemplate.exchange("/v1/products", HttpMethod.POST,
            new HttpEntity<>(request), Long.class).getBody();
        ResponseEntity<ProductDto> response = testRestTemplate.exchange("/v1/products/{productId}", HttpMethod.GET,
            null, ProductDto.class, productId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetProducts_ShouldReturnProducts() {
        testRestTemplate.exchange("/v1/products", HttpMethod.POST,
            new HttpEntity<>(request), Long.class);
        ResponseEntity<GetProductsResponse> response = testRestTemplate.exchange("/v1/products", HttpMethod.GET, null,
            GetProductsResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getProducts().isEmpty());
    }

    @Test
    void testUpdateProduct_WhenGivenUpdateProductRequest_ShouldReturnProduct() {
        Long productId = testRestTemplate.exchange("/v1/products", HttpMethod.POST,
            new HttpEntity<>(request), Long.class).getBody();
        UpdateProductRequest updateRequest = new UpdateProductRequest("Updated Product", "New Description",
            new BigDecimal(150), 20);
        ResponseEntity<ProductDto> response = testRestTemplate.exchange("/v1/products/{productId}", HttpMethod.PUT,
            new HttpEntity<>(updateRequest), ProductDto.class, productId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Product", response.getBody().getTitle());
    }

    @Test
    void testDeleteProduct_WhenGivenProductId_ShouldSuccess() {
        Long productId = testRestTemplate.exchange("/v1/products", HttpMethod.POST,
            new HttpEntity<>(request), Long.class).getBody();
        ResponseEntity<Void> response = testRestTemplate.exchange("/v1/products/{productId}", HttpMethod.DELETE, null,
            Void.class, productId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        ResponseEntity<ProductDto> getResponse = testRestTemplate.exchange("/v1/products/{productId}", HttpMethod.GET,
            null, ProductDto.class, productId);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }
}
