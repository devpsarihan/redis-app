package com.redis_app.controller.v1.response;

import com.redis_app.model.dto.ProductDto;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetProductResponse implements Serializable {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Integer count;
    private Long createdDate;
    private Long modifiedDate;

    public static GetProductResponse toResponse(final ProductDto product) {
        return GetProductResponse.builder()
            .id(product.getId())
            .title(product.getTitle())
            .description(product.getDescription())
            .price(product.getPrice())
            .count(product.getCount())
            .createdDate(product.getCreatedDate())
            .modifiedDate(product.getModifiedDate())
            .build();
    }
}
