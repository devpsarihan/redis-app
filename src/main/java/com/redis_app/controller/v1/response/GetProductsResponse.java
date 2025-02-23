package com.redis_app.controller.v1.response;

import com.redis_app.model.dto.ProductDto;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class GetProductsResponse implements Serializable {

    private List<GetProductResponse> products;

    public static GetProductsResponse toResponse(final List<ProductDto> products) {
        return GetProductsResponse.builder()
            .products(products.stream().map(GetProductResponse::toResponse).toList())
            .build();
    }
}
